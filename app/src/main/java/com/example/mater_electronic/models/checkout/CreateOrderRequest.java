package com.example.mater_electronic.models.checkout;

import com.example.mater_electronic.models.account.Address;

import java.util.List;

public class CreateOrderRequest {
    private String note;
    private String paymentMethod;
    private Address address;
    private List<CheckoutItem> listElectronics;

    public CreateOrderRequest(String note, String paymentMethod, Address address, List<CheckoutItem> listElectronics) {
        this.note = note;
        this.paymentMethod = paymentMethod;
        this.address = address;
        this.listElectronics = listElectronics;
    }
}
