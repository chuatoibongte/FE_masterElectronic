package com.example.mater_electronic.models.review;

import java.util.List;

public class Review {
    private String _id;
    private String userID;
    private String content;
    private String electronicID;
    private double rating;
    private List<ReviewImg> reviewImgs;
    private String createdAt;
    private String updatedAt;
    private double __v;

    public Review(String _id, String userID, String content, String electronicID, double rating, List<ReviewImg> reviewImgs, String createdAt, String updatedAt, double __v) {
        this._id = _id;
        this.userID = userID;
        this.content = content;
        this.electronicID = electronicID;
        this.rating = rating;
        this.reviewImgs = reviewImgs;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getElectronicID() {
        return electronicID;
    }

    public void setElectronicID(String electronicID) {
        this.electronicID = electronicID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<ReviewImg> getReviewImgs() {
        return reviewImgs;
    }

    public void setReviewImgs(List<ReviewImg> reviewImgs) {
        this.reviewImgs = reviewImgs;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double get__v() {
        return __v;
    }

    public void set__v(double __v) {
        this.__v = __v;
    }
}
