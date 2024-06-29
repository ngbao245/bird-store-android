package com.example.birdstoreandroid;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("Auth/SignInUser")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);
    @POST("Auth/SignUpUser")
    Call<SignUpResponse> userSignUp(@Body SignUpRequest registerRequest);
}
