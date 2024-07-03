package com.example.birdstoreandroid.IService;

import com.example.birdstoreandroid.Feature.Auth.LoginRequest;
import com.example.birdstoreandroid.Feature.Auth.LoginResponse;
import com.example.birdstoreandroid.Model.GetProductDetailRequest;
import com.example.birdstoreandroid.Model.GetProductDetailResponse;
import com.example.birdstoreandroid.Model.GetProductRequest;
import com.example.birdstoreandroid.Model.GetProductResponse;
import com.example.birdstoreandroid.Model.SignUpRequest;
import com.example.birdstoreandroid.Model.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("Auth/SignInUser")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);
    @POST("Auth/SignUpUser")
    Call<SignUpResponse> userSignUp(@Body SignUpRequest registerRequest);

    //GetProductList
    @GET("Product/GetProduct")
    Call<GetProductResponse> getProducts();

    @GET("Product/GetProductByID/{id}")
    Call<GetProductDetailResponse> getProductDetail(@Path("id") String productId);
}