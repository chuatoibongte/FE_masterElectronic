package com.example.mater_electronic.ui.tai_sd;
public class ProductItem {
    private final int imageResId;
    private final String name;
    private final String price;

    public ProductItem(int imageResId, String name, String price) {
        this.imageResId = imageResId;
        this.name = name;
        this.price = price;
    }

    public int getImageResId() { return imageResId; }
    public String getName() { return name; }
    public String getPrice() { return price; }
}
