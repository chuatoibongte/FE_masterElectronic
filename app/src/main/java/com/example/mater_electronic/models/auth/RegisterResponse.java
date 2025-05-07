package com.example.mater_electronic.models.auth;

import com.example.mater_electronic.models.account.Account;

public class RegisterResponse {
    private Boolean success;
    private Account data;
    public Boolean isSuccess(){ return success; }
    public Account getData() {
        return data;
    }
}
