package com.example.mater_electronic.ui.navigation.chabot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.repositories.ProductRepository;
import com.example.mater_electronic.models.displaydata.GetElectronicByIdResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatbotViewModel extends ViewModel {
    private final MutableLiveData<Product> productLiveData = new MutableLiveData<>();
    private final ProductRepository repository = new ProductRepository();

    public LiveData<Product> getProductLiveData() {
        return productLiveData;
    }

    public void getProductById(String productId) {
        repository.getProductDetail(productId, new Callback<GetElectronicByIdResponse>() {
            @Override
            public void onResponse(Call<GetElectronicByIdResponse> call, Response<GetElectronicByIdResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    productLiveData.postValue(response.body().getData());
                }
            }
            @Override
            public void onFailure(Call<GetElectronicByIdResponse> call, Throwable t) {
                productLiveData.postValue(null);
            }
        });
    }
}
