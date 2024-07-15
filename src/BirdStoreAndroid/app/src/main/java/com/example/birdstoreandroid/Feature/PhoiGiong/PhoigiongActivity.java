package com.example.birdstoreandroid.Feature.PhoiGiong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Model.CreatePhoiGiongRequest;
import com.example.birdstoreandroid.Model.CreatePhoiGiongResponse;
import com.example.birdstoreandroid.Model.GetProductRequest;
import com.example.birdstoreandroid.Model.GetProductResponse;
import com.example.birdstoreandroid.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoigiongActivity extends AppCompatActivity {
    private Spinner spMale, spFemale;
    private List<GetProductRequest> maleProducts, femaleProducts;
    Button btnPhoigiong, viewPhoigiong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phoigiong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spMale = findViewById(R.id.spMale);
        spFemale = findViewById(R.id.spFemale);
        btnPhoigiong = findViewById(R.id.btnPhoigiong);
        viewPhoigiong = findViewById(R.id.btnViewPhoiGiong);
        btnPhoigiong.setOnClickListener(v -> executePhoiGiong());
        viewPhoigiong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoigiongActivity.this, ListPhoigiongActivity.class);
                startActivity(intent);
            }
        });

        fetchProducts();
    }
    private void fetchProducts() {
        ApiClient.getUserService().getProducts().enqueue(new Callback<GetProductResponse>() {
            @Override
            public void onResponse(Call<GetProductResponse> call, Response<GetProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GetProductRequest> allProducts = response.body().getData();
                    filterProducts(allProducts);
                    setupSpinners();
                }
            }

            @Override
            public void onFailure(Call<GetProductResponse> call, Throwable t) {
                Toast.makeText(PhoigiongActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private GetProductRequest findProductByName(List<GetProductRequest> products, String name) {
        for (GetProductRequest product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    private void filterProducts(List<GetProductRequest> allProducts) {
        maleProducts = new ArrayList<>();
        femaleProducts = new ArrayList<>();
        for (GetProductRequest product : allProducts) {
            if (product.isSex()) {
                maleProducts.add(product);
            } else {
                femaleProducts.add(product);
            }
        }
    }

    private class ProductAdapter extends ArrayAdapter<GetProductRequest> {
        public ProductAdapter(Context context, List<GetProductRequest> products) {
            super(context, android.R.layout.simple_spinner_item, products);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getName());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getName());
            return view;
        }
    }

    private void setupSpinners() {
        ProductAdapter maleAdapter = new ProductAdapter(this, maleProducts);
        ProductAdapter femaleAdapter = new ProductAdapter(this, femaleProducts);

        spMale.setAdapter(maleAdapter);
        spFemale.setAdapter(femaleAdapter);
    }

    private void executePhoiGiong() {

        GetProductRequest selectedMale = (GetProductRequest) spMale.getSelectedItem();
        GetProductRequest selectedFemale = (GetProductRequest) spFemale.getSelectedItem();

        if (selectedMale == null || selectedFemale == null) {
            Toast.makeText(this, "Please select both male and female birds", Toast.LENGTH_SHORT).show();
            return;
        }

        CreatePhoiGiongRequest request = new CreatePhoiGiongRequest();
        request.setBird_Shop_Male(selectedMale.getId());
        request.setBird_Shop_Female(selectedFemale.getId());

        String accessToken = getAccessToken();

        RequestBody birdShopMale = RequestBody.create(MediaType.parse("text/plain"), selectedMale.getId());
        RequestBody birdShopFemale = RequestBody.create(MediaType.parse("text/plain"), selectedFemale.getId());

        ApiClient.getUserService().createPhoiGiong("Bearer " + accessToken, birdShopMale, birdShopFemale).enqueue(new Callback<CreatePhoiGiongResponse>() {
            @Override
            public void onResponse(Call<CreatePhoiGiongResponse> call, Response<CreatePhoiGiongResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CreatePhoiGiongResponse phoiGiongResponse = response.body();
                    // Handle successful response
                    Toast.makeText(PhoigiongActivity.this, "Phối giống successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PhoigiongActivity.this, ListPhoigiongActivity.class));
                } else {
                    // Handle error response
                    Toast.makeText(PhoigiongActivity.this, "Phối giống failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreatePhoiGiongResponse> call, Throwable t) {
                // Handle network or other errors
                Toast.makeText(PhoigiongActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("access_token", "");
    }
}