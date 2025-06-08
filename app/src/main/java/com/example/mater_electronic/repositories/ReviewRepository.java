package com.example.mater_electronic.repositories;

import android.content.Context;
import android.net.Uri;

import com.example.mater_electronic.models.review.CheckReviewExistResponse;
import com.example.mater_electronic.models.review.CreateReviewResponse;
import com.example.mater_electronic.network.ApiClient;
import com.example.mater_electronic.network.review.ReviewService;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

public class ReviewRepository {
    private ReviewService reviewServiceApi = ApiClient.getReviewService();

    public void createReview(String accessToken, String rating, String content, String electronicID, List<Uri> imageUris, Context context, Callback<CreateReviewResponse> callback) {

        String authHeader = "Bearer " + accessToken;

        RequestBody ratingBody = RequestBody.create(MediaType.parse("text/plain"), rating);
        RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), content);
        RequestBody electronicIDBody = RequestBody.create(MediaType.parse("text/plain"), electronicID);

        List<MultipartBody.Part> imageParts = prepareImagePartsFromUri(imageUris, context);

        reviewServiceApi.createReview(authHeader, ratingBody, contentBody, electronicIDBody, imageParts).enqueue(callback);
    }

    public void updateReview(String accessToken, String rating, String content, String electronicID, String commentID, List<Uri> imageUris, Context context, Callback<CreateReviewResponse> callback) {
        String authHeader = "Bearer " + accessToken;

        RequestBody ratingBody = RequestBody.create(MediaType.parse("text/plain"), rating);
        RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), content);
        RequestBody electronicIDBody = RequestBody.create(MediaType.parse("text/plain"), electronicID);
        RequestBody commentIDBody = RequestBody.create(MediaType.parse("text/plain"), commentID);

        List<MultipartBody.Part> imageParts = prepareImagePartsFromUri(imageUris, context);

        reviewServiceApi.updateReview(authHeader, ratingBody, contentBody, electronicIDBody, commentIDBody, imageParts).enqueue(callback);
    }

    private List<MultipartBody.Part> prepareImagePartsFromUri(List<Uri> imageUris, Context context) {
        List<MultipartBody.Part> parts = new ArrayList<>();

        for (int i = 0; i < imageUris.size(); i++) {
            Uri uri = imageUris.get(i);
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    byte[] bytes = readBytes(inputStream);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), bytes);

                    // Tên file giả định (tuỳ API, có thể cần đổi tên từng ảnh)
                    String fileName = "image_" + i + ".jpg";

                    MultipartBody.Part body = MultipartBody.Part.createFormData("reviewImgs", fileName, requestFile);
                    parts.add(body);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return parts;
    }

    private byte[] readBytes(InputStream inputStream) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    public void checkReviewExist(String accessToken, String electronicID, Callback<CheckReviewExistResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        reviewServiceApi.checkReviewExist(authHeader, electronicID).enqueue(callback);
    }
}
