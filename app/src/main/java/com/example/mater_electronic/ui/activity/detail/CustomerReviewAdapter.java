package com.example.mater_electronic.ui.activity.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivityProductDetailBinding;
import com.example.mater_electronic.models.review.Review;
import com.example.mater_electronic.models.review.ReviewImg;

import java.util.List;
import android.util.Log;

public class CustomerReviewAdapter extends RecyclerView.Adapter<CustomerReviewAdapter.CustomerReviewViewHolder> {
    private ActivityProductDetailBinding binding;
    private final Context mContext;

    private List<Review> mReviews;

    public CustomerReviewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Review> reviews) {
        this.mReviews = reviews;
        // load dữ liệu vào adapter
        notifyDataSetChanged();
    }

    public static class CustomerReviewViewHolder extends RecyclerView.ViewHolder {
        // các thành phần trong item
        public RatingBar ratingBar;
        public TextView tvCustomerReviewContent;
        public RecyclerView customerReviewImgRecyclerView;

        public CustomerReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            // ánh xạ view cho các thành phần
            ratingBar = itemView.findViewById(R.id.ratingBarCustomerReview);
            tvCustomerReviewContent = itemView.findViewById(R.id.tvCustomerReviewContent);
            customerReviewImgRecyclerView = itemView.findViewById(R.id.customerReviewImgRecyclerView);
        }
    }

    @NonNull
    @Override
    public CustomerReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new CustomerReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerReviewAdapter.CustomerReviewViewHolder holder, int position) {
        Review review = mReviews.get(position);
        if(review == null) return;
        holder.ratingBar.setRating((float) review.getRating());
        holder.tvCustomerReviewContent.setText(review.getContent());
        // ✅ Log nội dung đánh giá
        Log.d("ReviewDebug", "Nội dung: " + review.getContent());
        // ✅ Log danh sách ảnh đánh giá
        if (review.getReviewImgs() == null || review.getReviewImgs().isEmpty()) {
            Log.d("ReviewDebug", "Không có ảnh review cho đánh giá này.");
        } else {
            for (ReviewImg img : review.getReviewImgs()) {
                Log.d("ReviewDebug", "Ảnh review: " + img.getUrl());
            }
        }
        // reviewImgRecyclerView
//        ReviewImgAdapter reviewImgAdapter = new ReviewImgAdapter(review.getReviewImgs());
//        holder.customerReviewImgRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
//        if (review.getReviewImgs() == null || review.getReviewImgs().isEmpty()) {
//            holder.customerReviewImgRecyclerView.setVisibility(View.GONE);
//        } else {
//            holder.customerReviewImgRecyclerView.setVisibility(View.VISIBLE);
//            holder.customerReviewImgRecyclerView.setAdapter(reviewImgAdapter);
//        }
        if (review.getReviewImgs() == null || review.getReviewImgs().isEmpty()) {
            holder.customerReviewImgRecyclerView.setVisibility(View.GONE);
        } else {
            holder.customerReviewImgRecyclerView.setVisibility(View.VISIBLE);
            ReviewImgAdapter reviewImgAdapter = new ReviewImgAdapter(review.getReviewImgs());
            holder.customerReviewImgRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            holder.customerReviewImgRecyclerView.setAdapter(reviewImgAdapter);
        }

    }

    @Override
    public int getItemCount() {
        if(mReviews != null) return mReviews.size();
        return 0;
    }
}
