package com.example.mater_electronic.network.favorite;

import com.example.mater_electronic.models.favourite.AddFavouriteRequest;
import com.example.mater_electronic.models.favourite.AddFavouriteResponse;
import com.example.mater_electronic.models.favourite.CheckFavouriteResponse;
import com.example.mater_electronic.models.favourite.DeleteFavouriteRequest;
import com.example.mater_electronic.models.favourite.DeleteFavouriteResponse;
import com.example.mater_electronic.models.favourite.GetFavouriteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FavoriteService {
    @GET("customer/favorite")
    Call<GetFavouriteResponse> getFavorite(@Header("Authorization") String authHeader);
    @POST("customer/favorite")
    Call<AddFavouriteResponse> addFavorite(@Header("Authorization") String authHeader, @Body AddFavouriteRequest request);
    @GET("customer/favorite/check")
    Call<CheckFavouriteResponse> checkFavourite(@Header("Authorization") String authHeader, @Query("electronicID") String electronicID);
    @DELETE("customer/favorite/{electronicID}")
    Call<DeleteFavouriteResponse> deleteFavorite(@Header("Authorization") String authHeader, @Path("electronicID") String electronicID);
}
