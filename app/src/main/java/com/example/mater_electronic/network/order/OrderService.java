package com.example.mater_electronic.network.order;

import com.example.mater_electronic.models.checkout.CreateOrderRequest;
import com.example.mater_electronic.models.checkout.CreateOrderResponse;
import com.example.mater_electronic.models.myorder.CancelOrderResponse;
import com.example.mater_electronic.models.myorder.GetOrderElectronicsRequest;
import com.example.mater_electronic.models.myorder.GetOrderElectronicsResponse;
import com.example.mater_electronic.models.myorder.GetOrderRequest;
import com.example.mater_electronic.models.myorder.GetOrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderService {
    @POST("customer/order")
    Call<CreateOrderResponse> createOrder(@Header("Authorization") String authHeader, @Body CreateOrderRequest request);
    @GET("customer/order")
    Call<GetOrderResponse> getOrderByUserIDandStatus(@Header("Authorization") String authHeader, @Query("status") String status);
    @GET("customer/order/electronics")
    Call<GetOrderElectronicsResponse> getElectronicsByOrderID(@Header("Authorization") String authHeader, @Query("id") String id);
    @PATCH("customer/order/{id}")
    Call<CancelOrderResponse> cancelOrder(@Header("Authorization") String authHeader, @Path("id") String id);
    @PATCH("customer/order/{id}/received")
    Call<CancelOrderResponse> receivedOrder(@Header("Authorization") String authHeader, @Path("id") String id);
}
