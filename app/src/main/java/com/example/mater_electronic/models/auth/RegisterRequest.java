package com.example.mater_electronic.models.auth;

public class RegisterRequest {
    private String username;
    private String email;
    private String phone;
    private String password;

    public RegisterRequest(String username, String email, String phone, String password) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
