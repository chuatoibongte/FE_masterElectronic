package com.example.mater_electronic.repositories;

import com.example.mater_electronic.models.favourite.AddFavouriteRequest;
import com.example.mater_electronic.models.favourite.AddFavouriteResponse;
import com.example.mater_electronic.models.favourite.GetFavouriteResponse;
import com.example.mater_electronic.network.ApiClient;
import com.example.mater_electronic.network.favorite.FavoriteService;

import retrofit2.Callback;

public class FavouriteRepository {
    private FavoriteService favoriteService = ApiClient.getFavouriteService();
    public void getFavorite(String accessToken, Callback<GetFavouriteResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        favoriteService.getFavorite(authHeader).enqueue(callback);
    }
    public void addFavorite(String accessToken, AddFavouriteRequest request, Callback<AddFavouriteResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        favoriteService.addFavorite(authHeader, request).enqueue(callback);
    }
}
