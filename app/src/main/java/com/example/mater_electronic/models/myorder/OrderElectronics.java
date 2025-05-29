package com.example.mater_electronic.models.myorder;

public class OrderElectronics {
    private OrderElectronicID electronicID;
    private int quantity;
    private String _id;

    public OrderElectronics(OrderElectronicID electronicID, int quantity, String _id) {
        this.electronicID = electronicID;
        this.quantity = quantity;
        this._id = _id;
    }

    public OrderElectronicID getElectronicID() {
        return electronicID;
    }

    public void setElectronicID(OrderElectronicID electronicID) {
        this.electronicID = electronicID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
