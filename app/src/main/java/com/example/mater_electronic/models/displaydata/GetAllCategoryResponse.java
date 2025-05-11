package com.example.mater_electronic.models.displaydata;

import java.util.List;

public class GetAllCategoryResponse {
    private boolean success;
    private List<String> data;

    public boolean isSuccess() {
        return success;
    }

    public List<String> getData() {
        return data;
    }
}
