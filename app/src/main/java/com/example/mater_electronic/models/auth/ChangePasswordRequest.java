package com.example.mater_electronic.models.auth;

public class ChangePasswordRequest {
    public String email;
    public String otp;
    public String newpass;

    public ChangePasswordRequest(String email, String otp, String newpass) {
        this.email = email;
        this.otp = otp;
        this.newpass = newpass;
    }
}
