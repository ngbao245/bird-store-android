package com.example.birdstoreandroid.Feature.Cart;

import android.content.Intent;
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
import com.example.birdstoreandroid.Feature.Order.OrderActivity;
import com.example.birdstoreandroid.Feature.ZaloPay.OrderPayment;
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
    private TextView totalTxt;
    private AppCompatButton checkoutBtn;
    private ArrayList<String> listIDCarts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.store_cart_layout);
        cartRecyclerView = findViewById(R.id.recyclerViewCart);
        totalTxt = findViewById(R.id.textViewTotalPrice);
        checkoutBtn = findViewById(R.id.buttonCheckout);
        updateCheckoutButtonState();

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartItems);
        cartRecyclerView.setAdapter(cartAdapter);

        ImageView btnBack = findViewById(R.id.back_button);
        btnBack.setOnClickListener(v -> finish());

        String userId = getUserId();

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
        fetchCartItems();
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
                            cartRecyclerView.setVisibility(View.GONE);
                        } else {
                            cartRecyclerView.setVisibility(View.VISIBLE);
                        }

                        for (CartItem cartItem : cartItems) {
                            String cartItemId = cartItem.getId();
                            // Do something with the cartItemId, e.g., add it to a list or perform some operation
                            listIDCarts.add(cartItemId);
                        }

                        updateCartSummary();
                        updateCheckoutButtonState();
                    } else {
                        // Handle error
                        Toast.makeText(CartActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle error
                    Toast.makeText(CartActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                }
                updateCheckoutButtonState();
            }

            @Override
            public void onFailure(Call<GetCartResponse> call, Throwable t) {
                // Handle failure
                Toast.makeText(CartActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                updateCheckoutButtonState();
            }
        });
    }

    private void updateCartSummary() {
        float total = 0;

        for (CartItem cartItem : cartItems) {
            total += cartItem.getProduct().getPrice();
        }

        totalTxt.setText(String.format("%.0f vnđ", total));
    }
    private void updateCheckoutButtonState() {
        checkoutBtn.setEnabled(!cartItems.isEmpty());
        checkoutBtn.setAlpha(cartItems.isEmpty() ? 0.5f : 1.0f);
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
