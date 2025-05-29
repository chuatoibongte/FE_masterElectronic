package com.example.mater_electronic.repositories;

import com.example.mater_electronic.models.auth.RegisterResponse;
import com.example.mater_electronic.models.checkout.CreateOrderRequest;
import com.example.mater_electronic.models.checkout.CreateOrderResponse;
import com.example.mater_electronic.models.myorder.GetOrderResponse;
import com.example.mater_electronic.network.ApiClient;
import com.example.mater_electronic.network.order.OrderService;

import retrofit2.Callback;

public class OrderRepository {
    private OrderService orderService = ApiClient.getOrderService();
    public void createOrder(String accessToken, CreateOrderRequest createOrderRequest, Callback<CreateOrderResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        orderService.createOrder(authHeader, createOrderRequest).enqueue(callback);
    }
    public void getOrderByUserIDandStatus(String accessToken, String status, Callback<GetOrderResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        orderService.getOrderByUserIDandStatus(authHeader, status).enqueue(callback);
    }
}
