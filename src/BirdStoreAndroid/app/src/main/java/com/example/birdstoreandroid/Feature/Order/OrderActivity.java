package com.example.birdstoreandroid.Feature.Order;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Feature.Cart.CartActivity;
import com.example.birdstoreandroid.Feature.Cart.CartAdapter;
import com.example.birdstoreandroid.Feature.Cart.CartItem;
import com.example.birdstoreandroid.Model.CreateOrderRequest;
import com.example.birdstoreandroid.Model.CreateOrderResponse;
import com.example.birdstoreandroid.Model.GetCartResponse;
import com.example.birdstoreandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderItems);
        orderRecyclerView.setAdapter(orderAdapter);

        //data create order
        listIDCarts.add("2147ca2e26d74261a2c518b636a780b6");

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrderActivity.this, "Success", Toast.LENGTH_SHORT).show();
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
                        orderItems.clear();
                        orderItems.addAll(cartResponse.getData());
                        orderAdapter.notifyDataSetChanged();

                        if (orderItems.isEmpty()) {
                            orderRecyclerView.setVisibility(View.GONE);
                        } else {
                            orderRecyclerView.setVisibility(View.VISIBLE);
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
}
