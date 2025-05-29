package com.example.mater_electronic.models.checkout;

public class CheckoutItem {
    private String electronicID;
    private int quantity;

    public String getElectronicID() {
        return electronicID;
    }

    public void setElectronicID(String electronicID) {
        this.electronicID = electronicID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
