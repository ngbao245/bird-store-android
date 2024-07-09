package com.example.birdstoreandroid.Feature.GetProduct;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Activity.UserActivity;
import com.example.birdstoreandroid.Feature.Cart.CartActivity;
import com.example.birdstoreandroid.Feature.GetCategory.GetCategoryActivity;
import com.example.birdstoreandroid.Feature.GetCategory.GetCategoryAdapter;
import com.example.birdstoreandroid.Feature.GoogleMap.MapsActivity;
import com.example.birdstoreandroid.Model.GetCartResponse;
import com.example.birdstoreandroid.Model.GetCategoryRequest;
import com.example.birdstoreandroid.Model.GetCategoryResponse;
import com.example.birdstoreandroid.Model.GetProductRequest;
import com.example.birdstoreandroid.Model.GetProductResponse;
import com.example.birdstoreandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProductActivity extends AppCompatActivity implements GetProductAdapter.OnItemClickListener {

    private GetProductAdapter productAdapter;
    private RecyclerView product_recycler_view;

    private ImageView userIcon, location_icon;
    private TextView viewCategory;
    private GetCategoryAdapter categoryAdapter;
    private RecyclerView category_recycler_view;

    private FloatingActionButton cart_button;
    private SearchView searchView;
    private TextView delivery_address_input;

    private static final int MAPS_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_main_layout);

        delivery_address_input = findViewById(R.id.delivery_address_input);
        location_icon = findViewById(R.id.location_icon);

        if (delivery_address_input.getText().toString().trim().isEmpty()) {
            Intent intent = new Intent(GetProductActivity.this, MapsActivity.class);
            startActivityForResult(intent, MAPS_ACTIVITY_REQUEST_CODE);
        }

        delivery_address_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetProductActivity.this, MapsActivity.class);
                startActivityForResult(intent, MAPS_ACTIVITY_REQUEST_CODE);
            }
        });

        location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetProductActivity.this, MapsActivity.class);
                startActivityForResult(intent, MAPS_ACTIVITY_REQUEST_CODE);
            }
        });

        location_icon = findViewById(R.id.location_icon);
        location_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetProductActivity.this, MapsActivity.class);
                startActivityForResult(intent, MAPS_ACTIVITY_REQUEST_CODE);
            }
        });

        product_recycler_view = findViewById(R.id.product_recycler_view);
        category_recycler_view = findViewById(R.id.category_recycler_view);
        cart_button = findViewById(R.id.cart_button);
        searchView = findViewById(R.id.search_view);

        product_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        category_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        userIcon = findViewById(R.id.user_icon);
        userIcon.setOnClickListener(v -> {
            Intent intent = new Intent(GetProductActivity.this, UserActivity.class);
            startActivity(intent);
        });

        viewCategory = findViewById(R.id.categories_listall_label);
        viewCategory.setOnClickListener(v -> {
            Intent intent = new Intent(GetProductActivity.this, GetCategoryActivity.class);
            startActivity(intent);
        });

        cart_button.setOnClickListener(v -> {
            Intent intent = new Intent(GetProductActivity.this, CartActivity.class);
            startActivity(intent);
        });

        fetchProducts();
        fetchCategories();
        getAllCart();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (productAdapter != null) {
                    productAdapter.filter(newText);
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAPS_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String address = data.getStringExtra("address");
                delivery_address_input.setText(address);
            }
        }
    }

    private void fetchCategories() {
        ApiClient.getUserService().getCategories().enqueue(new Callback<GetCategoryResponse>() {
            @Override
            public void onResponse(Call<GetCategoryResponse> call, Response<GetCategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GetCategoryRequest> categories = response.body().getData();
                    categoryAdapter = new GetCategoryAdapter(categories);
                    category_recycler_view.setAdapter(categoryAdapter);
                } else {
                    Toast.makeText(GetProductActivity.this, "Failed to fetch categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {
                Toast.makeText(GetProductActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProducts() {
        ApiClient.getUserService().getProducts().enqueue(new Callback<GetProductResponse>() {
            @Override
            public void onResponse(Call<GetProductResponse> call, Response<GetProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GetProductRequest> products = response.body().getData();
                    productAdapter = new GetProductAdapter(GetProductActivity.this, products, GetProductActivity.this);
                    product_recycler_view.setAdapter(productAdapter);
                } else {
                    Toast.makeText(GetProductActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProductResponse> call, Throwable t) {
                Toast.makeText(GetProductActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllCart() {
        String accessToken = getAccessToken();
        ApiClient.getUserService().getCart("Bearer " + accessToken).enqueue(new Callback<GetCartResponse>() {
            @Override
            public void onResponse(Call<GetCartResponse> call, Response<GetCartResponse> response) {
                if (response.isSuccessful()) {
                    // Handle cart data
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<GetCartResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }

    @Override
    public void onItemClick(GetProductRequest product) {
        String productId = product.getId();
        Intent intent = new Intent(GetProductActivity.this, ProductDetailActivity.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }

    private String getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("access_token", "");
    }
}
