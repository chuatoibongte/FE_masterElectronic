package com.example.mater_electronic.repositories;

import com.example.mater_electronic.models.auth.RegisterResponse;
import com.example.mater_electronic.models.checkout.CreateOrderRequest;
import com.example.mater_electronic.models.checkout.CreateOrderResponse;
import com.example.mater_electronic.models.myorder.CancelOrderResponse;
import com.example.mater_electronic.models.myorder.GetOrderElectronicsResponse;
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

    public void getElectronicsByOrderID(String accessToken, String id, Callback<GetOrderElectronicsResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        orderService.getElectronicsByOrderID(authHeader, id).enqueue(callback);
    }

    public void cancelOrder(String accessToken, String id, Callback<CancelOrderResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        orderService.cancelOrder(authHeader, id).enqueue(callback);
    }
    public void receivedOrder(String accessToken, String id, Callback<CancelOrderResponse> callback) {
        String authHeader = "Bearer " + accessToken;
        orderService.receivedOrder(authHeader, id).enqueue(callback);
    }
}
