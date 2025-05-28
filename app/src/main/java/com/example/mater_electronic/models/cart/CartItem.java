package com.example.mater_electronic.models.cart;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "cart_items")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "product_id")
    private String productId;

    @ColumnInfo(name = "product_name")
    private String productName;

    @ColumnInfo(name = "product_image")
    private String productImage;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "date_added")
    private Date dateAdded;

    @ColumnInfo(name = "is_selected")
    private boolean isSelected;

    @ColumnInfo(name = "user_id")
    private String userId; // To support multiple users

    // Constructors
    public CartItem() {
        this.dateAdded = new Date();
        this.isSelected = true;
    }

    public CartItem(String productId, String productName, String productImage,
                    double price, int quantity, String category, String userId) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.userId = userId;
        this.dateAdded = new Date();
        this.isSelected = true;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Helper method to calculate total price
    public double getTotalPrice() {
        return price * quantity;
    }
}