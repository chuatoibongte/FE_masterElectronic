package com.example.mater_electronic.ui.activity.profile.myorder;

public interface MyOrderActionListener {
    void onCancelOrder(String orderId);
    void onConfirmReceived(String orderId);
}