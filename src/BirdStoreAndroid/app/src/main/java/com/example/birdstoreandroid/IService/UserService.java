package com.example.birdstoreandroid.IService;

import com.example.birdstoreandroid.Feature.Auth.LoginRequest;
import com.example.birdstoreandroid.Feature.Auth.LoginResponse;
import com.example.birdstoreandroid.Model.GetProductResponse;
import com.example.birdstoreandroid.Model.SignUpRequest;
import com.example.birdstoreandroid.Model.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @POST("Auth/SignInUser")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);
    @POST("Auth/SignUpUser")
    Call<SignUpResponse> userSignUp(@Body SignUpRequest registerRequest);

    //GetProductList
    @GET("Product/GetProduct")
    Call<GetProductResponse> getProducts();
}
