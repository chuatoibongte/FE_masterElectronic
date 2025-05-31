package com.example.mater_electronic.ui.activity.profile.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.favourite.AddFavouriteRequest;
import com.example.mater_electronic.models.favourite.AddFavouriteResponse;
import com.example.mater_electronic.models.favourite.CheckFavouriteResponse;
import com.example.mater_electronic.models.favourite.DeleteFavouriteRequest;
import com.example.mater_electronic.models.favourite.DeleteFavouriteResponse;
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
    private MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
    public LiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }
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
    public void checkFavourite(String accessToken, String electronicID) {
        favoriteRepository.checkFavourite(accessToken, electronicID, new Callback<CheckFavouriteResponse>() {
            @Override
            public void onResponse(Call<CheckFavouriteResponse> call, Response<CheckFavouriteResponse> response) {
                if (!response.isSuccessful()) {
                    errorMessage.setValue("Lỗi: " + response.code());
                    return;
                }
                assert response.body() != null;
                isFavorite.setValue(response.body().isStatus());
            }
            @Override
            public void onFailure(Call<CheckFavouriteResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }
    public void deleteFavorite(String accessToken, String electronicID){
        favoriteRepository.deleteFavorite(accessToken, electronicID, new Callback<DeleteFavouriteResponse>(){
            @Override
            public void onResponse(Call<DeleteFavouriteResponse> call, Response<DeleteFavouriteResponse> response) {
                if (!response.isSuccessful()) {
                    errorMessage.setValue("Lỗi: " + response.code());
                    return;
                }
                resultMessage.setValue("Xóa sản phẩm yêu thích thành công");
            }
            @Override
            public void onFailure(Call<DeleteFavouriteResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }
}
