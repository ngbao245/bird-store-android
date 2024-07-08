package com.example.birdstoreandroid.Feature.GetProduct;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Model.AddToCartRequest;
import com.example.birdstoreandroid.Model.AddToCartResponse;
import com.example.birdstoreandroid.Model.GetProductDetailResponse;
import com.example.birdstoreandroid.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    TextView pName, pPrice, pDesc, pGender, pQuantity;
    ImageView pImage, add, remove;
    AppCompatButton addToCart;
    private String productId;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_product_detail);
        setContentView(R.layout.store_detail_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pName = findViewById(R.id.product_name);
        pPrice = findViewById(R.id.product_price);
        pDesc = findViewById(R.id.product_description);
        pGender = findViewById(R.id.product_gender);
        pImage = findViewById(R.id.product_image);
        add = findViewById(R.id.add_item);
        remove = findViewById(R.id.remove_item);
        pQuantity = findViewById(R.id.quantity);
        addToCart = findViewById(R.id.add_to_cart);
        productId = getIntent().getStringExtra("productId");
        fetchProductDetails();

        ImageView btnBack = findViewById(R.id.back_button);
        btnBack.setOnClickListener(v -> finish());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                updateQuantity();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    updateQuantity();
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void updateQuantity() {
        pQuantity.setText(String.valueOf(quantity));
    }

    private String getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("access_token", "");
    }

    private void addToCart() {
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setProduct_id(productId);
        String accessToken = getAccessToken();
        ApiClient.getUserService().addToCart("Bearer " + accessToken, addToCartRequest).enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatusCode() == 200) {
                    AddToCartResponse.CartItem cartItem = response.body().getData();
                    Toast.makeText(ProductDetailActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
                    // Optionally, you can update the UI or perform any other actions after adding the item to the cart
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProductDetails() {
        ApiClient.getUserService().getProductDetail(productId).enqueue(new Callback<GetProductDetailResponse>() {
            @Override
            public void onResponse(Call<GetProductDetailResponse> call, Response<GetProductDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GetProductDetailResponse.ProductDetail productDetail = response.body().getData();
                    displayProductDetails(productDetail);
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Failed to fetch product details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProductDetailResponse> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayProductDetails(GetProductDetailResponse.ProductDetail productDetail) {
        pName.setText(productDetail.getName());
        pPrice.setText(productDetail.getPrice() + " VND");
        pDesc.setText(productDetail.getDescription());
        pGender.setText(productDetail.isSex() ? "Male" : "Female");
        Picasso.get()
                .load(productDetail.getImage())
                .into(pImage);
    }
}