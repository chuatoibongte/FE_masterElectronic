package com.example.mater_electronic.models.myorder;

import java.util.List;

public class GetOrderResponse {
    private boolean success;
    private List<Order> data;
    public boolean isSuccess() {
        return success;
    }
    public List<Order> getOrders() {
        return data;
    }
}
