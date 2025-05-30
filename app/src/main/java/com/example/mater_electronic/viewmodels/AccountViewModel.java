package com.example.mater_electronic.viewmodels;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.Address;
import com.example.mater_electronic.models.account.ChangePassAccountRequest;
import com.example.mater_electronic.models.account.ChangePassAccountResponse;
import com.example.mater_electronic.models.account.GetAccountResponse;
import com.example.mater_electronic.models.account.UpdateAccountResponse;
import com.example.mater_electronic.repositories.AccountRespository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountViewModel extends ViewModel {
    private final AccountRespository accountRespository = new AccountRespository();
    private final MutableLiveData<Account> accountLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> resultMessage = new MutableLiveData<>();
    public LiveData<String> getResultMessage() {
        return resultMessage;
    }

    public LiveData<Account> getAccountLiveData() {
        return accountLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getUpdateSuccess() {
        return updateSuccess;
    }

    public void getAccount(String accessToken) {
        accountRespository.getAccount(accessToken, new Callback<GetAccountResponse>() {
            @Override
            public void onResponse(Call<GetAccountResponse> call, Response<GetAccountResponse> response) {
                Log.e("AccountViewModel", "Account: " + response.toString());
                if (!response.isSuccessful()) {
                    errorMessage.setValue("Lỗi: " + response.code());
                    return;
                }
                assert response.body() != null;
                Account account = response.body().getData();
                Log.e("AccountViewModel", "Account:" + account.get_id());
                accountLiveData.setValue(account);
            }

            @Override
            public void onFailure(Call<GetAccountResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }

    //Change pass account
    public void changePass(String accessToken, String password, String newpassword){
        ChangePassAccountRequest request = new ChangePassAccountRequest(password, newpassword);

        accountRespository.changePasss(accessToken, request, new Callback<ChangePassAccountResponse>(){
            @Override
            public void onResponse(Call<ChangePassAccountResponse> call, Response<ChangePassAccountResponse> response){
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        resultMessage.setValue("Đổi mật khẩu thành công;");
                    } else {
                        resultMessage.setValue("Đổi mật khẩu thất bại");
                    }
                } else {
                    resultMessage.setValue("Lỗi server: " + response.message());
                }

                assert response.body() != null;
                updateSuccess.setValue(response.body().isSuccess());
            }

            @Override
            public void onFailure(Call<ChangePassAccountResponse> call, Throwable t){
                errorMessage.setValue("Lỗi: " + t.getMessage());
                updateSuccess.setValue(false);
            }
        });
    }

    //Update Address
    public void updateAddress(String accessToken, List<Address> addressList){
        accountRespository.updateAddressList(accessToken, addressList, new Callback<UpdateAccountResponse>() {
            @Override
            public void onResponse(Call<UpdateAccountResponse> call, Response<UpdateAccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        resultMessage.setValue("Cập nhật danh sách địa chỉ thành công;");
                    } else {
                        resultMessage.setValue("Cập nhật danh sách địa chỉ thất bại");
                    }
                } else {
                    resultMessage.setValue("Lỗi server: " + response.message());
                }

                assert response.body() != null;
                updateSuccess.setValue(response.body().isSuccess());
            }

            @Override
            public void onFailure(Call<UpdateAccountResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
                updateSuccess.setValue(false);
            }
        });
    }

    // Update account with image
    public void updateAccount(String accessToken, Account updatedAccount, Uri imageUri, android.content.Context context) {
        accountRespository.updateAccount(accessToken, updatedAccount, imageUri, context, new Callback<UpdateAccountResponse>() {
            @Override
            public void onResponse(Call<UpdateAccountResponse> call, Response<UpdateAccountResponse> response) {
                if (!response.isSuccessful()) {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
                        Log.e("API_UPDATE_ERROR", "Code: " + response.code() + ", Body: " + errorBody);
                        errorMessage.setValue("Lỗi cập nhật: " + response.code());
                    } catch (Exception e) {
                        Log.e("API_UPDATE_ERROR", "Không đọc được lỗi từ server", e);
                        errorMessage.setValue("Lỗi không xác định từ server");
                    }
                    updateSuccess.setValue(false);
                    return;
                }
                assert response.body() != null;
                Account account = response.body().getData();
                accountLiveData.setValue(account);
                updateSuccess.setValue(true);
            }

            @Override
            public void onFailure(Call<UpdateAccountResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
                updateSuccess.setValue(false);
            }
        });
    }

    // Update account without image
    public void updateAccount(String accessToken, Account updatedAccount, android.content.Context context) {
        updateAccount(accessToken, updatedAccount, null, context);
    }
}