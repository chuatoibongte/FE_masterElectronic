package com.example.mater_electronic.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.auth.SendOTPRequest;
import com.example.mater_electronic.models.auth.SendOTPResponse;
import com.example.mater_electronic.repositories.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOTPViewModel extends ViewModel {
    private final AuthRepository repo = new AuthRepository();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> resultMessage = new MutableLiveData<>();
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<String> getResultMessage() {
        return resultMessage;
    }
    public void sendOTP(String email) {
        isLoading.setValue(true);
        SendOTPRequest request = new SendOTPRequest(email);

        repo.sendOTP(request, new Callback<SendOTPResponse>() {
            @Override
            public void onResponse(Call<SendOTPResponse> call, Response<SendOTPResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        resultMessage.setValue("Gửi OTP thành công, vui lòng kiểm tra mail của bạn");
                    } else {
                        resultMessage.setValue("Gửi OTP thất bại");
                    }
                } else {
                    resultMessage.setValue("Lỗi server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SendOTPResponse> call, Throwable t) {
                isLoading.setValue(false);
                resultMessage.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
