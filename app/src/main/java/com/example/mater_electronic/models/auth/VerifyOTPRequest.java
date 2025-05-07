package com.example.mater_electronic.models.auth;

public class VerifyOTPRequest {
    private String email;
    private String otp;

    public VerifyOTPRequest(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }
}
