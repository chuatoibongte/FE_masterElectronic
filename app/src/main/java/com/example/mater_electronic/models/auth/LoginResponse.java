package com.example.mater_electronic.models.auth;

import com.example.mater_electronic.models.account.Account;

public class LoginResponse {
    private Boolean success;
    private Account data;
    private String accessToken;
    public Boolean isSuccess(){ return success; }
    public Account getData() {
        return data;
    }
    public String getAccessToken() {
        return accessToken;
    }
}
