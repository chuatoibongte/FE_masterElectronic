package com.example.mater_electronic.ui.activity.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.utils.LoadImageByUrl;

import java.util.List;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ProductImageViewHolder> {

    List<ProductImageItem> productImageList;

    public ProductImageAdapter(List<ProductImageItem> productImageList) {
        this.productImageList = productImageList;
    }

    @NonNull
    @Override
    public ProductImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_image_item, parent, false);
        return new ProductImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageViewHolder holder, int position) {
        // xử lí dữ liệu cho item
        ProductImageItem item = productImageList.get(position % productImageList.size()); // Hỗ trợ vòng lặp vô hạn
        LoadImageByUrl.loadImage(holder.productImage, item.getUrl());
    }

    @Override
    public int getItemCount() {
        return productImageList.isEmpty() ? 0 : Integer.MAX_VALUE; // Vòng lặp vô hạn
    }

    public static class ProductImageViewHolder extends RecyclerView.ViewHolder {
        // xử lí cái item
        ImageView productImage;
        public ProductImageViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}
