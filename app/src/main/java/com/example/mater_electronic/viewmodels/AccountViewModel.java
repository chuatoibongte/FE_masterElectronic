package com.example.mater_electronic.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.GetAccountResponse;
import com.example.mater_electronic.repositories.AccountRespository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountViewModel extends ViewModel {
    private final AccountRespository accountRespository = new AccountRespository();
    private final MutableLiveData<Account> accountLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<Account> getAccountLiveData() { return accountLiveData; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public void getAccount(String accessToken){
        accountRespository.getAccount(accessToken, new Callback<GetAccountResponse>(){
            @Override
            public void onResponse(Call<GetAccountResponse> call, Response<GetAccountResponse> response){
                Log.e("AccountViewModel", "Account: "+ response.toString());
                if(!response.isSuccessful()){
                    errorMessage.setValue("Lỗi: " + response.code());
                    return;
                }
                assert response.body() != null;
                Account account = response.body().getData();
                Log.e("AccountViewModel", "Account:" + account.get_id());
                accountLiveData.setValue(account);
            }
            @Override
            public void onFailure(Call<GetAccountResponse> call, Throwable t){
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }
}
