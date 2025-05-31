package com.example.mater_electronic.ui.activity.profile.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.favourite.AddFavouriteRequest;
import com.example.mater_electronic.models.favourite.AddFavouriteResponse;
import com.example.mater_electronic.models.favourite.GetFavouriteResponse;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.repositories.FavouriteRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFavoriteViewModel extends ViewModel {
    private MutableLiveData<String> resultMessage = new MutableLiveData<>();
    private FavouriteRepository favoriteRepository = new FavouriteRepository();
    private MutableLiveData<List<Product>> favoriteProducts = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<Boolean> getUpdateSuccess() {
        return updateSuccess;
    }
    public LiveData<String> getResultMessage() {
        return resultMessage;
    }
    public LiveData<List<Product>> getFavoriteProducts() {
        return favoriteProducts;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void getFavorite(String accessToken) {
        favoriteRepository.getFavorite(accessToken, new Callback<GetFavouriteResponse>() {
            @Override
            public void onResponse(Call<GetFavouriteResponse> call, Response<GetFavouriteResponse> response) {
                if (!response.isSuccessful()) {
                    errorMessage.setValue("Lỗi: " + response.code());
                    return;
                }
                assert response.body() != null;
                List<Product> products = response.body().getData();
                favoriteProducts.setValue(products);
            }

            @Override
            public void onFailure(Call<GetFavouriteResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }
    public void addFavourite(String accessToken, String electronicID) {
        AddFavouriteRequest addFavouriteRequest = new AddFavouriteRequest(electronicID);
        favoriteRepository.addFavorite(accessToken, addFavouriteRequest, new Callback<AddFavouriteResponse>() {
            @Override
            public void onResponse(Call<AddFavouriteResponse> call, Response<AddFavouriteResponse> response) {
                if (!response.isSuccessful()) {
                    errorMessage.setValue("Lỗi: " + response.code());
                    return;
                }
                resultMessage.setValue("Thêm sản phẩm yêu thích thành công");
            }

            @Override
            public void onFailure(Call<AddFavouriteResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }
}
