package com.example.mater_electronic.network.auth;

import com.example.mater_electronic.models.auth.ChangePasswordResponse;
import com.example.mater_electronic.models.auth.LoginRequest;
import com.example.mater_electronic.models.auth.LoginResponse;
import com.example.mater_electronic.models.auth.RegisterRequest;
import com.example.mater_electronic.models.auth.RegisterResponse;
import com.example.mater_electronic.models.auth.SendOTPRequest;
import com.example.mater_electronic.models.auth.SendOTPResponse;
import com.example.mater_electronic.models.auth.VerifyOTPRequest;
import com.example.mater_electronic.models.auth.VerifyOTPResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    //API đăng ký
    @POST("/user/accountAction/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);
    //API đăng nhập
    @POST("/user/accountAction/login")
    Call<LoginResponse> login(@Body LoginRequest request);
    //API gửi OTP
    @POST("/user/accountAction/sendOTP")
    Call<SendOTPResponse> sendOTP(@Body SendOTPRequest email);
    //API xác nhận OTP
    @POST("/user/accountAction/verifyOTP")
    Call<VerifyOTPResponse> verifyOTP(@Body VerifyOTPRequest request);

    @FormUrlEncoded
    @POST("/user/accountAction/changepassbyOTP")
    Call<ChangePasswordResponse> changePassword(
            @Field("email") String email,
            @Field("otp") String otp,
            @Field("newpass") String newPassword
    );
}

