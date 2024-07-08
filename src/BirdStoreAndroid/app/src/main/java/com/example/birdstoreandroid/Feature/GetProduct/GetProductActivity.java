package com.example.birdstoreandroid.Feature.GetProduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Feature.GetCategory.GetCategoryAdapter;
import com.example.birdstoreandroid.Model.GetCategoryRequest;
import com.example.birdstoreandroid.Model.GetCategoryResponse;
import com.example.birdstoreandroid.Model.GetProductRequest;
import com.example.birdstoreandroid.Model.GetProductResponse;
import com.example.birdstoreandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProductActivity extends AppCompatActivity implements GetProductAdapter.OnItemClickListener {
//    private ListView listView;
    private GetProductAdapter productAdapter;
    private RecyclerView product_recycler_view;

    private GetCategoryAdapter categoryAdapter;
    private RecyclerView category_recycler_view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_get_product);
        setContentView(R.layout.store_main_layout);

//        listView = findViewById(R.id.lvGetProduct);

        product_recycler_view = findViewById(R.id.product_recycler_view);
        category_recycler_view = findViewById(R.id.category_recycler_view);

        product_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        category_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        fetchProducts();

        fetchCategories();

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