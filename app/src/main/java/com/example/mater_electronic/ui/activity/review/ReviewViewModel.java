package com.example.mater_electronic.ui.activity.review;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mater_electronic.models.review.CheckReviewExistResponse;
import com.example.mater_electronic.models.review.CreateReviewResponse;
import com.example.mater_electronic.models.review.DeleteReviewResponse;
import com.example.mater_electronic.models.review.Review;
import com.example.mater_electronic.repositories.ReviewRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewViewModel extends AndroidViewModel {
    private ReviewRepository reviewRepository = new ReviewRepository();

    private MutableLiveData<Boolean> isReviewSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> isUpdateReviewSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDeleteReviewSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> checkIsExistedMessage = new MutableLiveData<>();
    private MutableLiveData<Review> existedReview = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<Boolean> getIsReviewSuccess() {
        return isReviewSuccess;
    }
    public LiveData<Boolean> getIsUpdateReviewSuccess() {
        return isUpdateReviewSuccess;
    }
    public LiveData<Boolean> getIsDeleteReviewSuccess() {
        return isDeleteReviewSuccess;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getCheckIsExistedMessage() {
        return checkIsExistedMessage;
    }
    public LiveData<Review> getExistedReview() {
        return existedReview;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public ReviewViewModel(@NonNull Application application) {
        super(application);
    }
    public void createReview(String accessToken, String rating, String content, String electronicID, List<Uri> imageUris, android.content.Context context){
        isLoading.setValue(true);
        reviewRepository.createReview(accessToken, rating, content, electronicID, imageUris, context, new Callback<CreateReviewResponse>() {

            @Override
            public void onResponse(Call<CreateReviewResponse> call, Response<CreateReviewResponse> response) {
                if (response.isSuccessful()) {
                    isReviewSuccess.setValue(true);
                    isLoading.setValue(false);
                } else {
                    isReviewSuccess.setValue(false);
                    errorMessage.postValue("Gửi đánh giá thất bại: " + response.message());
                    isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<CreateReviewResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }
    public void updateReview(String accessToken, String rating, String content, String electronicID, String commentID, List<Uri> imageUris, android.content.Context context){
        isLoading.setValue(true);
        reviewRepository.updateReview(accessToken, rating, content, electronicID, commentID, imageUris, context, new Callback<CreateReviewResponse>() {

            @Override
            public void onResponse(Call<CreateReviewResponse> call, Response<CreateReviewResponse> response) {
                if (response.isSuccessful()) {
                    isUpdateReviewSuccess.setValue(true);
                    isLoading.setValue(false);
                } else {
                    isUpdateReviewSuccess.setValue(false);
                    errorMessage.postValue("Gửi đánh giá thất bại: " + response.message());
                    isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<CreateReviewResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }

    public void deleteReview(String accessToken, String electronicID, String commentID){
        isLoading.setValue(true);
        reviewRepository.deleteReview(accessToken, electronicID, commentID, new Callback<DeleteReviewResponse>() {
            @Override
            public void onResponse(Call<DeleteReviewResponse> call, Response<DeleteReviewResponse> response) {
                if(response.isSuccessful()) {
                    isDeleteReviewSuccess.setValue(true);
                    isLoading.setValue(false);
                } else {
                    isDeleteReviewSuccess.setValue(false);
                    errorMessage.postValue("Xóa đánh giá thất bại: " + response.message());
                    isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<DeleteReviewResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }
    public void checkReviewExist(String accessToken, String electronicID){
        isLoading.setValue(true);
        reviewRepository.checkReviewExist(accessToken, electronicID, new Callback<CheckReviewExistResponse>() {
            @Override
            public void onResponse(Call<CheckReviewExistResponse> call, Response<CheckReviewExistResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    checkIsExistedMessage.setValue(response.body().getMessage());
                    if(response.body().getMessage().equals("existed")) {
                        // Log.e("Existed review: ", response.body().getReview().getContent());
                        existedReview.setValue(response.body().getReview());
                    }
                    isLoading.setValue(false);
                } else {
                    errorMessage.postValue("Error: " + response.message());
                    isLoading.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<CheckReviewExistResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
                isLoading.setValue(false);
            }
        });
    }
}
