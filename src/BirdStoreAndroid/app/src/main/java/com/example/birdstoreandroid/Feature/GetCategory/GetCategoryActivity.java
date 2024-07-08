package com.example.birdstoreandroid.Feature.GetCategory;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Feature.GetProduct.GetProductActivity;
import com.example.birdstoreandroid.Model.GetCategoryRequest;
import com.example.birdstoreandroid.Model.GetCategoryResponse;
import com.example.birdstoreandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCategoryActivity extends AppCompatActivity {
    private GetCategoryAdapter categoryAdapter;
    private RecyclerView category_recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_category_layout);

        category_recycler_view = findViewById(R.id.categoryRecyclerView);
        category_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        ImageView btnBack = findViewById(R.id.back_button);
        btnBack.setOnClickListener(v -> finish());

        fetchCategories();
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
                    Toast.makeText(GetCategoryActivity.this, "Failed to fetch categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCategoryResponse> call, Throwable t) {
                Toast.makeText(GetCategoryActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}