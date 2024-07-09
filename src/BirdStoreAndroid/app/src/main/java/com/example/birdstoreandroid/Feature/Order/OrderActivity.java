package com.example.birdstoreandroid.Feature.Order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Activity.UserActivity;
import com.example.birdstoreandroid.Feature.Cart.CartActivity;
import com.example.birdstoreandroid.Feature.Cart.CartItem;
import com.example.birdstoreandroid.Feature.GetProduct.GetProductActivity;
import com.example.birdstoreandroid.Feature.Notification.NotificationHelper;
import com.example.birdstoreandroid.Feature.ZaloPay.Api.CreateOrder;
import com.example.birdstoreandroid.Model.CreateOrderRequest;
import com.example.birdstoreandroid.Model.CreateOrderResponse;
import com.example.birdstoreandroid.Model.GetCartResponse;
import com.example.birdstoreandroid.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private TextView TvLocation, TvDistance;

    private TextView categories_listall_label;

    private double totalZalo;

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
        categories_listall_label = findViewById(R.id.categories_listall_label);

        ImageView btnBack = findViewById(R.id.back_button);
        btnBack.setOnClickListener(v -> finish());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(2553, Environment.SANDBOX);

        fetchLocationAndDistance();

        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderItems);
        orderRecyclerView.setAdapter(orderAdapter);

        categories_listall_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, GetProductActivity.class);
                startActivity(intent);
                finish();
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    String totalString = String.format("%.0f", totalZalo);
                    JSONObject data = orderApi.createOrder(totalString);
                    String code = data.getString("return_code");
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(OrderActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String transactionId, String s1, String s2) {
                                Intent intent1 = new Intent(OrderActivity.this, UserActivity.class);
                                intent1.putExtra("result", "Thanh toán thành công");
                                startActivity(intent1);
                                createOrder(transactionId);
                                finish();
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent1 = new Intent(OrderActivity.this, CartActivity.class);
                                intent1.putExtra("result", "Hủy thanh toán");
                                Log.d("PaymentStatus", "Hủy thanh toán");
                                startActivity(intent1);
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent1 = new Intent(OrderActivity.this, CartActivity.class);
                                intent1.putExtra("result", "Lỗi thanh toán");
                                Log.d("PaymentStatus", "Lỗi thanh toán");
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

                        sendCustomNotification(
                                "Order Success",
                                "Your order has been placed",
                                "Thank you for shopping with us. Your order is on its way!");

                        Intent intent = new Intent(OrderActivity.this, UserActivity.class);
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
                            listIDCarts.add(cartItemId);
                        }

                        totalZalo = updateOrderSummary();
                    } else {
                        Toast.makeText(OrderActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OrderActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCartResponse> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double updateOrderSummary() {
        double subTotal = 0;
        if (orderItems != null && !orderItems.isEmpty()) {
            for (CartItem cartItem : orderItems) {
                subTotal += cartItem.getProduct().getPrice();
            }
        } else {
            Toast.makeText(OrderActivity.this, "No items in the cart", Toast.LENGTH_SHORT).show();
        }

        // Hardcoded delivery and tax for demo purposes
        double delivery = calculateDeliveryFee();
        double tax = 0.02 * subTotal; // Calculate tax based on subtotal

        double total = subTotal + delivery + tax;

        subTotalTxt.setText(String.format("%.0f vnđ", subTotal));
        deliveryTxt.setText(String.format("%.0f vnđ", delivery));
        taxTxt.setText("2%");
        totalTxt.setText(String.format("%.0f vnđ", total));

        return total;
    }

    private double calculateDeliveryFee() {
        SharedPreferences sharedPreferences = getSharedPreferences("location_distance", MODE_PRIVATE);
        float userDistance = sharedPreferences.getFloat("userDistance", 0);

        double deliveryFee = 0;

        if (userDistance < 2) {
            deliveryFee = 25000;
        } else if (userDistance >= 2 && userDistance < 5) {
            deliveryFee = 30000;
        } else if (userDistance >= 5 && userDistance < 10) {
            deliveryFee = 35000;
        } else {
            deliveryFee = 4000 * userDistance;
        }

        return deliveryFee;
    }

    private String getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("access_token", "");
    }

    private void fetchLocationAndDistance() {
        SharedPreferences sharedPreferences = getSharedPreferences("location_distance", MODE_PRIVATE);
        String userLocation = sharedPreferences.getString("userLocation", "No location found");
        float userDistance = sharedPreferences.getFloat("userDistance", 0);

        TvLocation = findViewById(R.id.textViewLocationTitle);
        TvDistance = findViewById(R.id.textViewDistanceTime);

        TvLocation.setText(userLocation);

        String formattedDistance;
        String time;

        if (userDistance < 1) {
            formattedDistance = String.format("%.2f", userDistance * 1000) + " m";
            time = calculateTime(userDistance / 20); // Convert km to hours
        } else {
            formattedDistance = String.format("%.2f", userDistance) + " km";
            time = calculateTime(userDistance / 20); // Convert km to hours
        }

        String distanceTimeResult = formattedDistance + " - " + time;
        TvDistance.setText(distanceTimeResult);
    }

    private String calculateTime(float distance) {
        int hours = (int) Math.floor(distance);
        float remainingDistance = distance - hours;
        int minutes = (int) (remainingDistance * 60);

        if (hours < 1) {
            return minutes + " minutes";
        } else {
            return hours + " hours";
        }
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("uid", MODE_PRIVATE);
        return sharedPreferences.getString("user", "");
    }

    @SuppressLint("MissingPermission")
    private void sendCustomNotification(String smallTitle, String largeTitle, String largeMessage) {
        // Create RemoteViews for custom notification layout
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.small_notification_layout);
        notificationLayout.setTextViewText(R.id.notification_title_small, smallTitle);

        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.large_notification_layout);
        notificationLayoutExpanded.setTextViewText(R.id.notification_title_large, largeTitle);
        notificationLayoutExpanded.setTextViewText(R.id.notification_body_large, largeMessage);

        // Add timestamp to the custom notification
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String strDate = sdf.format(new Date());
        notificationLayout.setTextViewText(R.id.tv_time_custom_notification, strDate);

        // Build and display the custom notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID_2)
                .setSmallIcon(R.drawable.ic_bird_small_icon)
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .setColor(getResources().getColor(R.color.orange));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(getNotificationId(), builder.build());
    }

    private int getNotificationId() {
        return (int) System.currentTimeMillis();
    }
}
