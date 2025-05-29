package com.example.mater_electronic.models.myorder;

import com.example.mater_electronic.models.product.ElectronicImg;

import java.util.List;

public class OrderElectronicID {
    private String _id;
    private String name;
    private List<ElectronicImg> electronicImgs;
    private double price;
    private double discount;
    private double rating;

    public OrderElectronicID(String _id, String name, List<ElectronicImg> electronicImgs, double price, double discount, double rating) {
        this._id = _id;
        this.name = name;
        this.electronicImgs = electronicImgs;
        this.price = price;
        this.discount = discount;
        this.rating = rating;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ElectronicImg> getElectronicImgs() {
        return electronicImgs;
    }

    public void setElectronicImgs(List<ElectronicImg> electronicImgs) {
        this.electronicImgs = electronicImgs;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
