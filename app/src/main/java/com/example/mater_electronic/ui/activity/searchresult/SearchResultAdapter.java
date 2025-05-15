package com.example.mater_electronic.ui.activity.searchresult;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.models.ProductItem;
import com.example.mater_electronic.utils.LoadImageByUrl;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private List<ProductItem> productList;

    public SearchResultAdapter(List<ProductItem> productList) {
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
        ProductItem productItem = productList.get(position);
        LoadImageByUrl.loadImage(holder.imgProduct, productItem.getImg());
        holder.tvName.setText(productItem.getName());
        holder.tvPrice.setText(String.valueOf(productItem.getPrice()) + "VNĐ");
        holder.ratingBar.setRating((float) productItem.getRating());
    }

    @Override
    public int getItemCount() {
        if(productList != null) return productList.size();
        return 0;
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
