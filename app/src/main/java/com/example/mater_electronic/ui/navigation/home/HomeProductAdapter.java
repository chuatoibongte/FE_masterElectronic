package com.example.mater_electronic.ui.navigation.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;


import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.ui.activity.detail.ProductDetailActivity;
import com.example.mater_electronic.utils.LoadImageByUrl;

import java.util.List;


public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder> {
    private final List<Product> products;
    public HomeProductAdapter(List<Product> products) {
        this.products = products;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product productItem = products.get(position);
        LoadImageByUrl.loadImage(holder.imgProduct, productItem.getElectronicImgs().get(0).getUrl());
        // holder.imgProduct.setImageResource(productItem.getImageResId());
        holder.tvName.setText(productItem.getName());
        holder.tvPrice.setText(String.valueOf(formatPrice(productItem.getPrice())) + "\nVNĐ");
        holder.ratingBar.setRating((float) productItem.getRating());
        holder.tvName.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
            intent.putExtra("product_id", productItem.get_id());
            v.getContext().startActivity(intent);
        });
        holder.cvProductItem.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.home_rv_animation));
    }
    @Override
    public int getItemCount() {
        return products.size();
    }
    private String formatPrice(double price) {
        return String.format("%,.0f", price);  // 1000000 -> 1.000.000₫
    }
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView cvProductItem;
        ImageView imgProduct, btnFavorite, btnAddToCart;
        TextView tvName, tvPrice;
        RatingBar ratingBar;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cvProductItem = itemView.findViewById(R.id.cvProductItem);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            ratingBar = itemView.findViewById(R.id.productRatingBar);
        }
    }
}
