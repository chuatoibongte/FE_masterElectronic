package com.example.mater_electronic.ui.navigation.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;


import com.example.mater_electronic.models.ProductItem;

import java.util.List;


public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder> {

    private final List<ProductItem> products;

    public HomeProductAdapter(List<ProductItem> products) {
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
        ProductItem item = products.get(position);
        holder.imgProduct.setImageResource(item.getImageResId());
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct, btnFavorite, btnAddToCart;
        TextView tvName, tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
