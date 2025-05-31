package com.example.mater_electronic.models.favourite;

import com.example.mater_electronic.models.product.Product;

public class AddFavouriteResponse {
    private boolean success;
    private Product data;
    public boolean isSuccess() {
        return success;
    }
    public Product getData() {
        return data;
    }
}
