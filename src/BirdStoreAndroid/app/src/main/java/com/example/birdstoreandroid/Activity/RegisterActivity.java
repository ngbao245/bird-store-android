package com.example.birdstoreandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.R;
import com.example.birdstoreandroid.Model.SignUpRequest;
import com.example.birdstoreandroid.Model.SignUpResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText userName, userPassword, userEmail, userPhone, addressLine;
    Button signup;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userName = findViewById(R.id.reUserName);
        userPassword = findViewById(R.id.rePassword);
        userEmail = findViewById(R.id.reEmail);
        userPhone = findViewById(R.id.rePhone);
        addressLine = findViewById(R.id.reAddress);
        signup = findViewById(R.id.buttonRegister);
        login = findViewById(R.id.linkLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(userPassword.getText().toString()) || TextUtils.isEmpty(userEmail.getText().toString()) || TextUtils.isEmpty(userPhone.getText().toString()) || TextUtils.isEmpty(addressLine.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    register();
                }
            }
        });
    }
    public void register() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUserName(userName.getText().toString());
        signUpRequest.setUserPassword(userPassword.getText().toString());
        signUpRequest.setUserEmail(userEmail.getText().toString());
        signUpRequest.setUserPhone(userPhone.getText().toString());
        signUpRequest.setRole_id("1");
        signUpRequest.setAddressLine(addressLine.getText().toString());
        Call<SignUpResponse> registerResponseCall = ApiClient.getUserService().userSignUp(signUpRequest);
        registerResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable throwable) {
                Toast.makeText(RegisterActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}