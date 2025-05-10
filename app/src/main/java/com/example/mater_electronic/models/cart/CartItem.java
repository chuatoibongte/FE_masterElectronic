package com.example.mater_electronic.models.cart;

public class CartItem {
    private String img;
    private String name;
    private double price;
    private int quantity;
    private String mainCategory;

    public CartItem(String img, String name, double price, int quantity, String mainCategory) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.mainCategory = mainCategory;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }
}
