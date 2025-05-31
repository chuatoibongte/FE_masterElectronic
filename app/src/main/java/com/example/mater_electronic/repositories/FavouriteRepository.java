package com.example.mater_electronic.repositories;

import com.example.mater_electronic.models.favourite.AddFavouriteRequest;
import com.example.mater_electronic.models.favourite.AddFavouriteResponse;
import com.example.mater_electronic.models.favourite.CheckFavouriteResponse;
import com.example.mater_electronic.models.favourite.DeleteFavouriteRequest;
import com.example.mater_electronic.models.favourite.DeleteFavouriteResponse;
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
    public void checkFavourite(String accessToken, String electronicID, Callback<CheckFavouriteResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        favoriteService.checkFavourite(authHeader, electronicID).enqueue(callback);
    }
    public void deleteFavorite(String accessToken, String electronicID, Callback<DeleteFavouriteResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        favoriteService.deleteFavorite(authHeader, electronicID).enqueue(callback);
    }
}
