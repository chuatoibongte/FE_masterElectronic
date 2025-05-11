package com.example.mater_electronic.models.displaydata;

import com.example.mater_electronic.models.product.Product;

import java.util.List;

public class GetElectronicByIdResponse {
    private boolean success;
    private Product data;

    public boolean isSuccess() {return success;}
    public Product getData() {return data;}
}
