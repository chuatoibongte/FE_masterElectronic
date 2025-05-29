package com.example.mater_electronic.network.favorite;

import com.example.mater_electronic.models.favourite.GetFavouriteResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface FavoriteService {
    @GET("customer/favorite")
    Call<GetFavouriteResponse> getFavorite(@Header("Authorization") String authHeader);
}
