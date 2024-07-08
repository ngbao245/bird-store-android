package com.example.birdstoreandroid.Feature.Order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Activity.MainActivity;
import com.example.birdstoreandroid.Feature.Cart.CartActivity;
import com.example.birdstoreandroid.Feature.Cart.CartItem;
import com.example.birdstoreandroid.Feature.ZaloPay.Api.CreateOrder;
import com.example.birdstoreandroid.Feature.ZaloPay.OrderPayment;
import com.example.birdstoreandroid.Feature.ZaloPay.PaymentNotification;
import com.example.birdstoreandroid.Model.CreateOrderRequest;
import com.example.birdstoreandroid.Model.CreateOrderResponse;
import com.example.birdstoreandroid.Model.GetCartResponse;
import com.example.birdstoreandroid.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<CartItem> orderItems = new ArrayList<>();
    private TextView subTotalTxt;
    private TextView deliveryTxt;
    private TextView taxTxt;
    private TextView totalTxt;
    private AppCompatButton checkoutBtn;
    private ArrayList<String> listIDCarts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.store_order_layout);

        orderRecyclerView = findViewById(R.id.recyclerViewOrderDetails);
        subTotalTxt = findViewById(R.id.textViewTotalProduct);
        deliveryTxt = findViewById(R.id.textViewShippingFee);
        taxTxt = findViewById(R.id.textViewTax);
        totalTxt = findViewById(R.id.textViewTotal);
        checkoutBtn = findViewById(R.id.buttonCheckout);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(2553, Environment.SANDBOX);

        Intent intent = getIntent();

        //double total = Double.parseDouble(totalTxt.getText().toString().replace(" VNĐ", ""));
        double total = 10000;
        String totalString = String.format("%.0f", total);

        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderItems);
        orderRecyclerView.setAdapter(orderAdapter);

        String userId = getUserId();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(totalString);
                    String code = data.getString("return_code");
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(OrderActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String transactionId, String s1, String s2) {
//                                Intent intent1 = new Intent(OrderActivity.this, PaymentNotification.class);
//                                Log.d("GGGGGG", "Thanh toán thành công");
//                                startActivity(intent1);
                                createOrder(transactionId);
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent1 = new Intent(OrderActivity.this, CartActivity.class);
                                intent1.putExtra("result", "Hủy thanh toán");
                                Log.d("GGGGGG", "Hủy thanh toán");
                                startActivity(intent1);
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent1 = new Intent(OrderActivity.this, CartActivity.class);
                                intent1.putExtra("result", "Lỗi thanh toán");
                                Log.d("GGGGGG", "Lỗi thanh toán");
                                startActivity(intent1);
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        fetchCartItems();
    }
    private void createOrder(String transactionId) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setListIDCarts(listIDCarts);
        createOrderRequest.setUser_id(getUserId());
        createOrderRequest.setPaymentMenthod_id("20175b0e8fdd491292fcde60d1a45f41");
        createOrderRequest.setAddress("city");
        createOrderRequest.setTransactionId(transactionId);

        String accessToken = getAccessToken();
        ApiClient.getUserService().createOrder("Bearer " + accessToken, createOrderRequest).enqueue(new Callback<CreateOrderResponse>() {
            @Override
            public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                if (response.isSuccessful()) {
                    CreateOrderResponse createOrderResponse = response.body();
                    if (createOrderResponse != null && createOrderResponse.getStatusCode() == 200) {
                        Toast.makeText(OrderActivity.this, "Order created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OrderActivity.this, CartActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(OrderActivity.this, "Failed to create order", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OrderActivity.this, "Failed to create order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateOrderResponse> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    private void fetchCartItems() {
        String accessToken = getAccessToken();

        ApiClient.getUserService().getCart("Bearer " + accessToken).enqueue(new Callback<GetCartResponse>() {
            @Override
            public void onResponse(Call<GetCartResponse> call, Response<GetCartResponse> response) {
                if (response.isSuccessful()) {
                    GetCartResponse cartResponse = response.body();
                    if (cartResponse != null && cartResponse.getStatusCode() == 200) {
                        orderItems.clear();
                        orderItems.addAll(cartResponse.getData());
                        orderAdapter.notifyDataSetChanged();

                        if (orderItems.isEmpty()) {
                            orderRecyclerView.setVisibility(View.GONE);
                        } else {
                            orderRecyclerView.setVisibility(View.VISIBLE);
                        }

                        for (CartItem cartItem : orderItems) {
                            String cartItemId = cartItem.getId();
                            // Do something with the cartItemId, e.g., add it to a list or perform some operation
                            listIDCarts.add(cartItemId);
                        }

                        updateOrderSummary();
                    } else {
                        // Handle error
                        Toast.makeText(OrderActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle error
                    Toast.makeText(OrderActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCartResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(OrderActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateOrderSummary() {
        float subTotal = 0;
        float total = 0;
        float deliveryFee = 1;
        float taxFee = 1;

        for (CartItem orderItem : orderItems) {
            subTotal += orderItem.getProduct().getPrice();
        }

        total = subTotal + deliveryFee + taxFee;

        subTotalTxt.setText(String.valueOf(subTotal));
        deliveryTxt.setText(String.valueOf(deliveryFee));
        taxTxt.setText(String.valueOf(taxFee));
        totalTxt.setText(String.valueOf(total));
    }

    private String getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("access_token", "");
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("uid", MODE_PRIVATE);
        return sharedPreferences.getString("user", "");
    }
}
