package com.example.mater_electronic.models;
public class ProductItem {
    private final String img;
    private final String name;
    private final double price;

    private final double rating;

    public ProductItem(String img, String name, double price, double rating) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public String getImg() { return img; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public double getRating() {
        return rating;
    }
}
