package com.example.mater_electronic.ui.activity.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.models.review.ReviewImg;
import com.example.mater_electronic.utils.LoadImageByUrl;

import java.util.ArrayList;
import java.util.List;

public class ReviewImgAdapter extends RecyclerView.Adapter<ReviewImgAdapter.ReviewImgViewHolder> {
    private List<ReviewImg> mReviewImgs;
    public ReviewImgAdapter(List<ReviewImg> reviewImgs) {
        this.mReviewImgs = reviewImgs != null ? reviewImgs : new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewImgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_img_item, parent, false);
        return new ReviewImgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewImgViewHolder holder, int position) {
        ReviewImg reviewImg = mReviewImgs.get(position);
        if(reviewImg == null) return;
        LoadImageByUrl.loadImage(holder.reviewImg, reviewImg.getUrl());
    }

    @Override
    public int getItemCount() {
        if (mReviewImgs != null) return mReviewImgs.size();
        return 0;
    }

    public static class ReviewImgViewHolder extends RecyclerView.ViewHolder {
        ImageView reviewImg;
        public ReviewImgViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewImg = itemView.findViewById(R.id.reviewImg);
        }
    }

}
