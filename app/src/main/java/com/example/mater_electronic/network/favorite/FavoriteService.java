package com.example.mater_electronic.network.favorite;

import com.example.mater_electronic.models.favourite.AddFavouriteRequest;
import com.example.mater_electronic.models.favourite.AddFavouriteResponse;
import com.example.mater_electronic.models.favourite.GetFavouriteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FavoriteService {
    @GET("customer/favorite")
    Call<GetFavouriteResponse> getFavorite(@Header("Authorization") String authHeader);
    @POST("customer/favorite")
    Call<AddFavouriteResponse> addFavorite(@Header("Authorization") String authHeader, @Body AddFavouriteRequest request);
}
