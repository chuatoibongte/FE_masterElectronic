package com.example.mater_electronic.ui.activity.detail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.displaydata.GetElectronicByIdResponse;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.models.review.GetReviewResponse;
import com.example.mater_electronic.models.review.ReviewImg;
import com.example.mater_electronic.repositories.ProductRepository;

import java.util.List;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.mater_electronic.models.review.Review;

public class ProductViewModel extends ViewModel {

    private final ProductRepository repository = new ProductRepository();
    private final MutableLiveData<Product> productLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<Review>> productReviewLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<Product> getProductLiveData() {
        return productLiveData;
    }
    public LiveData<List<Review>> getProductReviewLiveData() {
        return productReviewLiveData;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void getProductDetail(String productId) {
        repository.getProductDetail(productId, new Callback<GetElectronicByIdResponse>() {
            @Override
            public void onResponse(Call<GetElectronicByIdResponse> call, Response<GetElectronicByIdResponse> response) {
                Log.e("ProductViewModel", "Response: " + response.toString() + "");
                if (response.isSuccessful()) {
                    GetElectronicByIdResponse getElectronicByIdResponse = response.body();
                    if (getElectronicByIdResponse != null && getElectronicByIdResponse.isSuccess()) {
                        Product product = getElectronicByIdResponse.getData();
                        productLiveData.setValue(product);
                    } else {
                        errorMessage.setValue("Không thể tải chi tiết sản phẩm");
                    }
                }

            }

            @Override
            public void onFailure(Call<GetElectronicByIdResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }
    public void getReviews(String productId) {
        repository.getReviews(productId, new Callback<GetReviewResponse>() {
            @Override
            public void onResponse(Call<GetReviewResponse> call, Response<GetReviewResponse> response) {
                Log.e("ProductViewModel", "Reviews: " + response.toString() + "");
                if (!response.isSuccessful()) {
                    errorMessage.setValue("Lỗi: " + response.code());
                    return;
                }
                assert response.body() != null;
                List<Review> reviews = response.body().getData();
                productReviewLiveData.setValue(reviews);
            }

            @Override
            public void onFailure(Call<GetReviewResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }
//    public void getReviews(String productId) {
//        List<Review> reviews = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            List<ReviewImg> imgs = new ArrayList<>();
//            imgs.add(new ReviewImg("https://via.placeholder.com/150?text=Review+" + i, "", ""));
//            reviews.add(new Review(
//                    "Reviewer " + i,
//                    "ID" + i,
//                    "Đánh giá số " + i,
//                    productId,
//                    4.5f,
//                    imgs,
//                    "2025-05-12",
//                    "2025-05-12",
//                    0
//            ));
//        }
//        productReviewLiveData.setValue(reviews);
//    }
}
