package com.example.mater_electronic.models.review;

import java.util.List;

public class GetReviewResponse {
    private boolean success;
    private List<Review> data;

    public boolean isSuccess() {return success;}
    public List<Review> getData() {return data;}
}
