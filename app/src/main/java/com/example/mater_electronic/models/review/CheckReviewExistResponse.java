package com.example.mater_electronic.models.review;

public class CheckReviewExistResponse {
    private boolean success;
    private String message;
    private Review review;
    public boolean isSuccess() {return success;}
    public String getMessage() {return message;}
    public Review getReview() {return review;}
}
