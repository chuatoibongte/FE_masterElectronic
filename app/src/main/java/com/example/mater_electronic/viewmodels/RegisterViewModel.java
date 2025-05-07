package com.example.mater_electronic.viewmodels;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.auth.RegisterRequest;
import com.example.mater_electronic.models.auth.RegisterResponse;
import com.example.mater_electronic.repositories.AuthRepository;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private final AuthRepository repo = new AuthRepository();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> resultMessage = new MutableLiveData<>();
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<String> getResultMessage() {
        return resultMessage;
    }
    public void registerAccount(String username, String email, String phone, String password) {
        isLoading.setValue(true);
        RegisterRequest request = new RegisterRequest(username,email,phone,password);

        repo.registerAccount(request, new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        resultMessage.setValue("Đăng ký thành công: " + response.body().getData().getUsername());
                    } else {
                        resultMessage.setValue("Đăng ký thất bại");
                    }
                } else {
                    resultMessage.setValue("Lỗi server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                isLoading.setValue(false);
                resultMessage.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
