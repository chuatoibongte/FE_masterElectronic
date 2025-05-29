package com.example.mater_electronic.models.myorder;

import com.example.mater_electronic.models.account.Address;

import java.util.List;

public class Order {
    private Address address;
    private String _id;
    private int quantity;
    private double totalPrice;
    private List<OrderElectronics> listElectronics;
    private String note;
    private String userID;
    private String paymentMethod;
    private String paymentStatus;
    private String status;
    private String time;
    private String createdAt;
    private String updatedAt;
    private int __v;

    public Order(Address address, String _id, int quantity, double totalPrice, List<OrderElectronics> listElectronics, String note, String userID, String paymentMethod, String paymentStatus, String status, String time, String createdAt, String updatedAt, int __v) {
        this.address = address;
        this._id = _id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.listElectronics = listElectronics;
        this.note = note;
        this.userID = userID;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.status = status;
        this.time = time;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderElectronics> getListElectronics() {
        return listElectronics;
    }

    public void setListElectronics(List<OrderElectronics> listElectronics) {
        this.listElectronics = listElectronics;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
