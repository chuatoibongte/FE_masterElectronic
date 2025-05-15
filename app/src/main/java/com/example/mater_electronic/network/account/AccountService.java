package com.example.mater_electronic.network.account;

import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.GetAccountResponse;
import com.example.mater_electronic.models.account.UpdateAccountResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;

public interface AccountService {
    //API lấy data Account
    @GET("/customer/manageAccount")
    Call<GetAccountResponse> getAccount(@Header("Authorization") String accessToken);
    //API Update data Account
    @PATCH("/customer/manageAccount")
    Call<UpdateAccountResponse> updateAccount(@Header("Authorization") String accessToken);
}
