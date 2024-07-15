package com.example.birdstoreandroid.IService;

import com.example.birdstoreandroid.Model.CreateOrderRequest;
import com.example.birdstoreandroid.Model.CreateOrderResponse;
import com.example.birdstoreandroid.Model.CreatePhoiGiongRequest;
import com.example.birdstoreandroid.Model.CreatePhoiGiongResponse;
import com.example.birdstoreandroid.Model.GetAllPhoigiongResponse;
import com.example.birdstoreandroid.Model.GetCategoryResponse;
import com.example.birdstoreandroid.Model.GetSingleUserResponse;
import com.example.birdstoreandroid.Model.LoginRequest;
import com.example.birdstoreandroid.Model.LoginResponse;
import com.example.birdstoreandroid.Model.AddToCartRequest;
import com.example.birdstoreandroid.Model.AddToCartResponse;
import com.example.birdstoreandroid.Model.GetCartResponse;
import com.example.birdstoreandroid.Model.GetProductDetailResponse;
import com.example.birdstoreandroid.Model.GetProductResponse;
import com.example.birdstoreandroid.Model.SignUpRequest;
import com.example.birdstoreandroid.Model.SignUpResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @POST("Auth/SignInUser")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @POST("Auth/SignUpUser")
    Call<SignUpResponse> userSignUp(@Body SignUpRequest registerRequest);

    //GetProductList
    @GET("Product/GetProduct")
    Call<GetProductResponse> getProducts();

    @GET("BirdCategory/GetAll")
    Call<GetCategoryResponse> getCategories();

    @GET("Product/GetProductByID/{id}")
    Call<GetProductDetailResponse> getProductDetail(@Path("id") String productId);

    @POST("Cart/api/Cart/Add-Product-To-Cart")
    Call<AddToCartResponse> addToCart(@Header("Authorization") String accessToken, @Body AddToCartRequest addToCartRequest);

    @GET("Cart/api/Cart/Get-All-Cart")
    Call<GetCartResponse> getCart(@Header("Authorization") String accessToken);

    @POST("Order/api/Order/Create-Order")
    Call<CreateOrderResponse> createOrder(@Header("Authorization") String token, @Body CreateOrderRequest createOrderRequest);

    //GetUser
    @GET("User/GetSingleID")
    Call<GetSingleUserResponse> getSingleUser(@Query("id") String id);

    @POST("PhoiGiong/Create-Phoi-Chim")
    @Multipart
    Call<CreatePhoiGiongResponse> createPhoiGiong(@Header("Authorization") String accessToken, @Part("bird_Shop_Male") RequestBody birdShopMale, @Part("bird_Shop_Female") RequestBody birdShopFemale);

    @GET("PhoiGiong/GetAllPhoiChim")
    Call<GetAllPhoigiongResponse> getAllPhoigiong(@Header("Authorization") String accessToken);
}
