package com.example.mater_electronic.models;
public class ProductItem {
    private final int imageResId;
    private final String name;
    private final double price;

    private final double rating;

    public ProductItem(int imageResId, String name, double price, double rating) {
        this.imageResId = imageResId;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public int getImageResId() { return imageResId; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public double getRating() {
        return rating;
    }
}
