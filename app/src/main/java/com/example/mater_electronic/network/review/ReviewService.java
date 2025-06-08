package com.example.mater_electronic.network.review;

import com.example.mater_electronic.models.review.CheckReviewExistResponse;
import com.example.mater_electronic.models.review.CreateReviewResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ReviewService {
    //API Cập nhật account sử dụng formdata
    @Multipart
    @POST("customer/comment")
    Call<CreateReviewResponse> createReview(
            @Header("Authorization") String authHeader,
            @Part("rating") RequestBody rating,
            @Part("content") RequestBody content,
            @Part("electronicID") RequestBody electronicID,
            @Part List<MultipartBody.Part> reviewImgs // Optional image file
    );
    // API check if review existed
    @GET("customer/comment/check")
    Call<CheckReviewExistResponse> checkReviewExist(@Header("Authorization") String authHeader, @Query("electronicID") String electronicID);
    // API update review
    @PATCH("customer/comment")
    Call<CreateReviewResponse> updateReview(
            @Header("Authorization") String authHeader,
            @Part("rating") RequestBody rating,
            @Part("content") RequestBody content,
            @Part("electronicID") RequestBody electronicID,
            @Part("commentID") RequestBody commentID,
            @Part List<MultipartBody.Part> reviewImgs // Optional image file
    );
}
