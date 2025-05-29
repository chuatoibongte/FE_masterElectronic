package com.example.mater_electronic.models.favourite;

import com.example.mater_electronic.models.product.Product;

import java.util.List;

public class GetFavouriteResponse {
    private boolean success;
    private List<Product> data;

    public boolean isSuccess() {
        return success;
    }

    public List<Product> getData() {
        return data;
    }
}
