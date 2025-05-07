package com.example.mater_electronic.repositories;

import com.example.mater_electronic.models.auth.LoginRequest;
import com.example.mater_electronic.models.auth.LoginResponse;
import com.example.mater_electronic.models.auth.RegisterRequest;
import com.example.mater_electronic.models.auth.RegisterResponse;
import com.example.mater_electronic.models.auth.SendOTPRequest;
import com.example.mater_electronic.models.auth.SendOTPResponse;
import com.example.mater_electronic.models.auth.VerifyOTPRequest;
import com.example.mater_electronic.models.auth.VerifyOTPResponse;
import com.example.mater_electronic.network.ApiClient;
import com.example.mater_electronic.network.auth.AuthService;

import retrofit2.Callback;

public class AuthRepository {
    private AuthService api = ApiClient.getApiService();
    public void registerAccount(RegisterRequest request, Callback<RegisterResponse> callback) {
        api.register(request).enqueue(callback);
    }
    public void loginAccount(LoginRequest request, Callback<LoginResponse> callback) {
        api.login(request).enqueue(callback);
    }
    public void sendOTP(SendOTPRequest email, Callback<SendOTPResponse> callback) {
        api.sendOTP(email).enqueue(callback);
    }
    public void verifyOTP(VerifyOTPRequest request, Callback<VerifyOTPResponse> callback) {
        api.verifyOTP(request).enqueue(callback);
    }
}
