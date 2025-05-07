package com.example.mater_electronic.network.auth;

import com.example.mater_electronic.models.auth.RegisterRequest;
import com.example.mater_electronic.models.auth.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/user/accountAction/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);
}

