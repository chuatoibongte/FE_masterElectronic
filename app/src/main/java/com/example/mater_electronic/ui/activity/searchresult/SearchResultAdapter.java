package com.example.mater_electronic.ui.activity.searchresult;

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
import com.example.mater_electronic.models.ProductItem;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.ui.activity.detail.ProductDetailActivity;
import com.example.mater_electronic.utils.LoadImageByUrl;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private List<Product> productList;

    public SearchResultAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public SearchResultAdapter.SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.SearchResultViewHolder holder, int position) {
        Product productItem = productList.get(position);
        LoadImageByUrl.loadImage(holder.imgProduct, productItem.getElectronicImgs().get(0).getUrl());
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
        if(productList != null) return productList.size();
        return 0;
    }
    private String formatPrice(double price) {
        return String.format("%,.0f", price);  // 1000000 -> 1.000.000₫
    }
    public class SearchResultViewHolder extends RecyclerView.ViewHolder {
        CardView cvProductItem;
        ImageView imgProduct, btnFavorite, btnAddToCart;
        TextView tvName, tvPrice;
        RatingBar ratingBar;
        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            ratingBar = itemView.findViewById(R.id.productRatingBar);
            cvProductItem = itemView.findViewById(R.id.cvProductItem);
        }
    }
}
