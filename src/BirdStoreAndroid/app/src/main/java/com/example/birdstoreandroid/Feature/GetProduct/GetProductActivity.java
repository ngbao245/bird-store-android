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

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Model.GetProductRequest;
import com.example.birdstoreandroid.Model.GetProductResponse;
import com.example.birdstoreandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProductActivity extends AppCompatActivity {
    private ListView listView;
    private GetProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_product);

        listView = findViewById(R.id.lvGetProduct);
        fetchProducts();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetProductRequest selectedProduct = (GetProductRequest) parent.getItemAtPosition(position);
                String productId = selectedProduct.getId();

                Intent intent = new Intent(GetProductActivity.this, ProductDetailActivity.class);
                intent.putExtra("productId", productId);
                startActivity(intent);
            }
        });
    }

    private void fetchProducts() {
        ApiClient.getUserService().getProducts().enqueue(new Callback<GetProductResponse>() {
            @Override
            public void onResponse(Call<GetProductResponse> call, Response<GetProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GetProductRequest> products = response.body().getData();
                    productAdapter = new GetProductAdapter(GetProductActivity.this, products);
                    listView.setAdapter(productAdapter);
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
}