package com.example.birdstoreandroid.IService;

import com.example.birdstoreandroid.Model.CreateOrderRequest;
import com.example.birdstoreandroid.Model.CreateOrderResponse;
import com.example.birdstoreandroid.Model.LoginRequest;
import com.example.birdstoreandroid.Model.LoginResponse;
import com.example.birdstoreandroid.Model.AddToCartRequest;
import com.example.birdstoreandroid.Model.AddToCartResponse;
import com.example.birdstoreandroid.Model.GetCartResponse;
import com.example.birdstoreandroid.Model.GetProductDetailRequest;
import com.example.birdstoreandroid.Model.GetProductDetailResponse;
import com.example.birdstoreandroid.Model.GetProductResponse;
import com.example.birdstoreandroid.Model.SignUpRequest;
import com.example.birdstoreandroid.Model.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @POST("Cart/api/Cart/Add-Product-To-Cart")
    Call<AddToCartResponse> addToCart(@Header("Authorization") String accessToken, @Body AddToCartRequest addToCartRequest);

    @GET("Cart/api/Cart/Get-All-Cart")
    Call<GetCartResponse> getCart(@Header("Authorization") String accessToken);

    @POST("Order/api/Order/Create-Order")
    Call<CreateOrderResponse> createOrder(@Header("Authorization") String token, @Body CreateOrderRequest createOrderRequest);
}
