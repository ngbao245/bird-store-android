package com.example.birdstoreandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.Model.GetProductDetailResponse;
import com.example.birdstoreandroid.Model.GetSingleUserResponse;
import com.example.birdstoreandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private AppCompatButton btnLogout;

    private String userId;

    TextView tv_userName, tv_userEmail, tv_userPhone, tv_addressLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_user_layout);

        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_userEmail = (TextView) findViewById(R.id.tv_userEmail);
        tv_userPhone = (TextView) findViewById(R.id.tv_userPhone);
        tv_addressLine = (TextView) findViewById(R.id.tv_addressLine);

        ImageView btnBack = findViewById(R.id.back_button);
        btnBack.setOnClickListener(v -> finish());

        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(UserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        fetchUserDetails();
    }

    private void fetchUserDetails() {
        userId = "5967ac6a0b244a789723e3cd2afac864";
        ApiClient.getUserService().getSingleUser(userId).enqueue(new Callback<GetSingleUserResponse>() {
            @Override
            public void onResponse(Call<GetSingleUserResponse> call, Response<GetSingleUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GetSingleUserResponse.UserDetail userDetail = response.body().getData();
                    displayUserDetail(userDetail);
                } else {
                    Toast.makeText(UserActivity.this, "Failed to fetch user details", Toast.LENGTH_SHORT).show();
                    Log.e("UserActivity", "Response code: " + response.code());
                    Log.e("UserActivity", "Response message: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("UserActivity", "Error body: " + response.errorBody().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSingleUserResponse> call, Throwable t) {
                Toast.makeText(UserActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("UserActivity", "API call failed: " + t.getMessage());
            }
        });
    }

    private void displayUserDetail(GetSingleUserResponse.UserDetail userDetail) {
        tv_userName.setText(userDetail.getUserName());
        tv_userEmail.setText(userDetail.getUserEmail());
        tv_userPhone.setText(userDetail.getUserPhone());
        tv_addressLine.setText(userDetail.getAddressLine());
    }
}
