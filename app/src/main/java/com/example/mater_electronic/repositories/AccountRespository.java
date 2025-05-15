package com.example.mater_electronic.repositories;

import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.GetAccountResponse;
import com.example.mater_electronic.models.account.UpdateAccountResponse;
import com.example.mater_electronic.network.ApiClient;
import com.example.mater_electronic.network.account.AccountService;

import retrofit2.Call;
import retrofit2.Callback;

public class AccountRespository {
    private AccountService accountServiceApi = ApiClient.getAccountService();

    //Lấy dữ liệu Account
    public void getAccount( String accessToken, Callback<GetAccountResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        accountServiceApi.getAccount(authHeader).enqueue(callback);
    }
    //Update dữ liệu Account
    public void updateAccount(String accessToken, Callback<UpdateAccountResponse> callback){
        String authHeader = "Bearer " + accessToken;
        accountServiceApi.updateAccount(authHeader).enqueue(callback);
    }
}
