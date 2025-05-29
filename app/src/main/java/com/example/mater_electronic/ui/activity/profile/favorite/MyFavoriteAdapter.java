package com.example.mater_electronic.ui.activity.profile.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mater_electronic.R;
import com.example.mater_electronic.models.product.Product;

import java.text.DecimalFormat;
import java.util.List;

public class MyFavoriteAdapter extends RecyclerView.Adapter<MyFavoriteAdapter.FavoriteViewHolder> {
    private Context context;
    private List<Product> favoriteProducts;
    private OnFavoriteClickListener onFavoriteClickListener;

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Product product);
    }

    public MyFavoriteAdapter(Context context, List<Product> favoriteProducts) {
        this.context = context;
        this.favoriteProducts = favoriteProducts;
    }

    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.onFavoriteClickListener = listener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Product product = favoriteProducts.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return favoriteProducts != null ? favoriteProducts.size() : 0;
    }

    public void updateFavoriteProducts(List<Product> newFavoriteProducts) {
        this.favoriteProducts = newFavoriteProducts;
        notifyDataSetChanged();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView tvElectronicName;
        private TextView tvDescription;
        private TextView sumPriceTxt;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            tvElectronicName = itemView.findViewById(R.id.tvElectronicName);
            tvDescription = itemView.findViewById(R.id.tvDiscription);
            sumPriceTxt = itemView.findViewById(R.id.sumPriceTxt);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onFavoriteClickListener != null) {
                    onFavoriteClickListener.onFavoriteClick(favoriteProducts.get(position));
                }
            });
        }

        public void bind(Product product) {
            // Set product name
            tvElectronicName.setText(product.getName());

            // Set product description
            tvDescription.setText(product.getDescription());

            // Set price
            DecimalFormat formatter = new DecimalFormat("#.###");
            String priceText = formatter.format(product.getPrice()) + " VNĐ";
            sumPriceTxt.setText(priceText);

            // Load product image using Glide
            if (product.getElectronicImgs() != null && !product.getElectronicImgs().isEmpty()) {
                String imageUrl = product.getElectronicImgs().get(0).getUrl();
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.edittext_background2)
                        .error(R.drawable.edittext_background2)
                        .into(productImage);
            } else {
                productImage.setImageResource(R.drawable.edittext_background2);
            }
        }
    }
}