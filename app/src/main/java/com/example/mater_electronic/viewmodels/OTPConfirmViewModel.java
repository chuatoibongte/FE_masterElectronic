package com.example.mater_electronic.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.auth.VerifyOTPRequest;
import com.example.mater_electronic.models.auth.VerifyOTPResponse;
import com.example.mater_electronic.repositories.AuthRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPConfirmViewModel extends ViewModel {
    private final AuthRepository repo = new AuthRepository();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> resultMessage = new MutableLiveData<>();
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<String> getResultMessage() {
        return resultMessage;
    }
    public void verifyOTP(String email, String otp) {
        isLoading.setValue(true);
        // create request form
        VerifyOTPRequest request = new VerifyOTPRequest(email, otp);
        // call api
        repo.verifyOTP(request, new Callback<VerifyOTPResponse>() {
            @Override
            public void onResponse(Call<VerifyOTPResponse> call, Response<VerifyOTPResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        resultMessage.setValue("Xác nhận OTP thành công");
                    } else {
                        resultMessage.setValue("Xác nhận OTP thất bại");
                    }
                }
                else {
                    resultMessage.setValue("Lỗi server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<VerifyOTPResponse> call, Throwable t) {
                isLoading.setValue(false);
                resultMessage.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
