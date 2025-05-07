package com.example.mater_electronic.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.auth.LoginRequest;
import com.example.mater_electronic.models.auth.LoginResponse;
import com.example.mater_electronic.repositories.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Sử dụng androidViewModel để có thể sử dụng context lưu SharedPrefs
public class LoginViewModel extends AndroidViewModel {
    private final AuthRepository repo = new AuthRepository();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> resultMessage = new MutableLiveData<>();
    private final Application application;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<String> getResultMessage() {
        return resultMessage;
    }
    public void loginAccount(String email, String password) {
        isLoading.setValue(true);
        LoginRequest request = new LoginRequest(email,password);

        repo.loginAccount(request, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    //Lưu thông tin người dùng vào SharedPreferences
                    if(response.body().isSuccess()){
                        SharedPreferences prefs = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        //Lưu các dữ liệu người dùng quan trọng vào SharedPreferences
                        editor.putString("accessToken", response.body().getAccessToken());
                        editor.putString("username", response.body().getData().getUsername());

                        editor.apply();
                        resultMessage.setValue("Đăng nhập thành công: " + response.body().getData().getUsername());
                    }else{
                        resultMessage.setValue("Đăng nhập thất bại");
                    }
                } else {
                    resultMessage.setValue("Lỗi server:  " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                isLoading.setValue(false);
                resultMessage.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
