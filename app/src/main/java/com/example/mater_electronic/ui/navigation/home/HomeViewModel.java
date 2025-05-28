package com.example.mater_electronic.ui.navigation.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.displaydata.GetSearchResultResponse;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.repositories.ProductRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private ProductRepository productRepository;
    private MutableLiveData<List<Product>> homeProducts = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Number> currentPage = new MutableLiveData<>();
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<List<Product>> getHomeProducts() {
        return homeProducts;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public LiveData<Number> getCurrentPage() {
        return currentPage;
    }
    public HomeViewModel() {
        productRepository = new ProductRepository();
    }
    public void getHomeProducts(int page, int limit) {
        isLoading.setValue(true);
        productRepository.getSearchResults("", "", "", "", "", page, limit, new Callback<GetSearchResultResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetSearchResultResponse> call, @NonNull Response<GetSearchResultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        homeProducts.setValue(response.body().getData());
                        isLoading.setValue(false);
                    }
                    else {
                        homeProducts.setValue(null);
                        isLoading.setValue(false);
                    }
                }
                else {
                    errorMessage.setValue("Lỗi server !!!");
                }
            }

            @Override
            public void onFailure(Call<GetSearchResultResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}