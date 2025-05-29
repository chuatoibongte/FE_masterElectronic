package com.example.mater_electronic.network.order;

import com.example.mater_electronic.models.checkout.CreateOrderRequest;
import com.example.mater_electronic.models.checkout.CreateOrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderService {
    @POST("customer/order")
    Call<CreateOrderResponse> createOrder(@Header("Authorization") String authHeader, @Body CreateOrderRequest request);
}
