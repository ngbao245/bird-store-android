package com.example.birdstoreandroid.Feature.Cart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Model.CreateOrderRequest;
import com.example.birdstoreandroid.Model.CreateOrderResponse;
import com.example.birdstoreandroid.Model.GetCartResponse;
import com.example.birdstoreandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems = new ArrayList<>();
    private TextView emptyTxt;
    private TextView subTotalTxt;
    private TextView deliveryTxt;
    private TextView taxTxt;
    private TextView totalTxt;
    private AppCompatButton orderBtn;
    private ImageView backBtn;
    private ArrayList<String> listIDCarts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cartRecyclerView = findViewById(R.id.cartView);
        emptyTxt = findViewById(R.id.emptyTxt);
        subTotalTxt = findViewById(R.id.subTotaltxt);
        deliveryTxt = findViewById(R.id.deliverytxt);
        taxTxt = findViewById(R.id.taxtxt);
        totalTxt = findViewById(R.id.totaltxt);
        orderBtn = findViewById(R.id.orderbtn);
        backBtn = findViewById(R.id.backBtn);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartItems);
        cartRecyclerView.setAdapter(cartAdapter);

        //data create order
        listIDCarts.add("9947bff7b8084e64842da7f159cae52f");

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrderRequest createOrderRequest = new CreateOrderRequest();
                createOrderRequest.setListIDCarts(listIDCarts);
                createOrderRequest.setUser_id("998c1178661a4c68a2b37a7289d72f87");
                createOrderRequest.setPaymentMenthod_id("20175b0e8fdd491292fcde60d1a45f41");
                createOrderRequest.setAddress("city");

                String accessToken = getAccessToken();
                ApiClient.getUserService().createOrder("Bearer " + accessToken, createOrderRequest).enqueue(new Callback<CreateOrderResponse>() {
                    @Override
                    public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                        if (response.isSuccessful()) {
                            CreateOrderResponse createOrderResponse = response.body();
                            if (createOrderResponse != null && createOrderResponse.getStatusCode() == 200) {
                                Toast.makeText(CartActivity.this, "Order created successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CartActivity.this, "Failed to create order", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CartActivity.this, "Failed to create order", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateOrderResponse> call, Throwable t) {
                        Toast.makeText(CartActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        fetchCartItems();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }
    private void fetchCartItems() {
        String accessToken = getAccessToken();

        ApiClient.getUserService().getCart("Bearer " + accessToken).enqueue(new Callback<GetCartResponse>() {
            @Override
            public void onResponse(Call<GetCartResponse> call, Response<GetCartResponse> response) {
                if (response.isSuccessful()) {
                    GetCartResponse cartResponse = response.body();
                    if (cartResponse != null && cartResponse.getStatusCode() == 200) {
                        cartItems.clear();
                        cartItems.addAll(cartResponse.getData());
                        cartAdapter.notifyDataSetChanged();

                        if (cartItems.isEmpty()) {
                            emptyTxt.setVisibility(View.VISIBLE);
                            cartRecyclerView.setVisibility(View.GONE);
                        } else {
                            emptyTxt.setVisibility(View.GONE);
                            cartRecyclerView.setVisibility(View.VISIBLE);
                        }

                        updateCartSummary();
                    } else {
                        // Handle error
                        Toast.makeText(CartActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle error
                    Toast.makeText(CartActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCartResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(CartActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCartSummary() {
        int subTotal = 0;
        int deliveryFee = 1; // TODO: Calculate delivery fee based on your logic
        int taxFee = 1; // TODO: Calculate tax fee based on your logic

        for (CartItem cartItem : cartItems) {
            subTotal += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        int total = subTotal + deliveryFee + taxFee;

        subTotalTxt.setText(String.valueOf(subTotal));
        deliveryTxt.setText(String.valueOf(deliveryFee));
        taxTxt.setText(String.valueOf(taxFee));
        totalTxt.setText(String.valueOf(total));
    }

    private String getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("access_token", "");
    }
}