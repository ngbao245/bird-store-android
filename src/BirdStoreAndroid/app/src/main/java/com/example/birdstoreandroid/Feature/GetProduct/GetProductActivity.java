package com.example.birdstoreandroid.Feature.GetProduct;

import android.content.Intent;
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
import com.example.birdstoreandroid.Activity.MainActivity;
import com.example.birdstoreandroid.Activity.UserActivity;
import com.example.birdstoreandroid.Feature.Cart.CartActivity;
import com.example.birdstoreandroid.Feature.GetCategory.GetCategoryActivity;
import com.example.birdstoreandroid.Feature.GetCategory.GetCategoryAdapter;
import com.example.birdstoreandroid.Feature.GoogleMap.MapsActivity;
import com.example.birdstoreandroid.Feature.Notification.CustomNotification;
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
    //    private ListView listView;
    private GetProductAdapter productAdapter;
    private RecyclerView product_recycler_view;

    private ImageView userIcon;

    private TextView viewCategory;

    private GetCategoryAdapter categoryAdapter;
    private RecyclerView category_recycler_view;

    private FloatingActionButton cart_button;

    private SearchView searchView;

    TextView delivery_address_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_get_product);
        setContentView(R.layout.store_main_layout);

//        listView = findViewById(R.id.lvGetProduct);

        delivery_address_input = (TextView) findViewById(R.id.delivery_address_input);
        if (delivery_address_input.getText().toString().trim().isEmpty()) {
            Intent intent = new Intent(GetProductActivity.this, MapsActivity.class);
            startActivity(intent);
        }

        product_recycler_view = findViewById(R.id.product_recycler_view);
        category_recycler_view = findViewById(R.id.category_recycler_view);

        cart_button = findViewById(R.id.cart_button);

        searchView = (SearchView) findViewById(R.id.search_view);

        product_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        category_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        userIcon = (ImageView) findViewById(R.id.user_icon);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetProductActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        viewCategory = (TextView) findViewById(R.id.categories_listall_label);
        viewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetProductActivity.this, GetCategoryActivity.class);
                startActivity(intent);
            }
        });

        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        fetchProducts();

        fetchCategories();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // No action on text submit
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (productAdapter != null) {
                    productAdapter.filter(newText);
                }
                return true;
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                GetProductRequest selectedProduct = (GetProductRequest) parent.getItemAtPosition(position);
//                String productId = selectedProduct.getId();
//
//                Intent intent = new Intent(GetProductActivity.this, ProductDetailActivity.class);
//                intent.putExtra("productId", productId);
//                startActivity(intent);
//            }
//        });
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

    @Override
    public void onItemClick(GetProductRequest product) {
        String productId = product.getId();

        Intent intent = new Intent(GetProductActivity.this, ProductDetailActivity.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }
}