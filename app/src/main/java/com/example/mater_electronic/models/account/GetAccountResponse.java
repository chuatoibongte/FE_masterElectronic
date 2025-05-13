package com.example.mater_electronic.models.account;

public class GetAccountResponse {
    private boolean success;
    private Account data;

    public boolean isSuccess() { return success; }
    public Account getData() { return data; }
}
