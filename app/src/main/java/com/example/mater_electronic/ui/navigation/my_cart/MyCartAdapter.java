package com.example.mater_electronic.ui.navigation.my_cart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mater_electronic.R;
import com.example.mater_electronic.database.cart.CartManager;
import com.example.mater_electronic.models.cart.CartItem;
import com.example.mater_electronic.ui.activity.detail.ProductDetailActivity;
import com.example.mater_electronic.ui.navigation.home.HomeProductAdapter;

import java.text.BreakIterator;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {
    private CartManager cartManager;

    private Context mContext;
    private List<CartItem> mCartItems;
    public MyCartAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<CartItem> cartItems) {
        this.mCartItems = cartItems;
        notifyDataSetChanged();
    }
    public void setCartManager(CartManager cartManager) {
        this.cartManager = cartManager;
    }
    public static class MyCartViewHolder extends RecyclerView.ViewHolder {
        CartManager cartManager;
        ImageView cartItemImg;
        TextView cartItemName;
        ImageView iconSelect, trashIcon;
        TextView cartItemType, cartItemPrice, txtQuantity;
        ImageView btnDecrease, btnIncrease;

        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImg = itemView.findViewById(R.id.cartItemImg);
            cartItemName = itemView.findViewById(R.id.cartItemName);
            iconSelect = itemView.findViewById(R.id.iconSelect);
            trashIcon = itemView.findViewById(R.id.trashIcon);
            cartItemType = itemView.findViewById(R.id.cartItemType);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
        }
    }

    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new MyCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.MyCartViewHolder holder, int position) {
        CartItem item = mCartItems.get(position);
        if (item == null) return;

        // Load ảnh
        Glide.with(holder.itemView.getContext())
                .load(item.getProductImage())
                .placeholder(R.drawable.processing)
                .error(R.drawable.processing)
                .into(holder.cartItemImg);

        // Set dữ liệu vào các view
        holder.cartItemName.setText(item.getProductName());
        // holder.cartItemType.setText(item.getType());
        holder.cartItemPrice.setText(formatPrice(item.getPrice()));
        holder.txtQuantity.setText(String.valueOf(item.getQuantity()));

        // Event: click item
        holder.cartItemName.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("product_id", item.getProductId());
                mContext.startActivity(intent);
                // Toast.makeText(mContext, "Clicked: " + item.getProductName(), Toast.LENGTH_SHORT).show();
        });

        // Event: tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() + 1;
            item.setQuantity(newQuantity);

            // Cập nhật database
            if (cartManager != null) {
                cartManager.updateQuantity(item.getId(), newQuantity);
            }

            // Cập nhật UI
            holder.txtQuantity.setText(String.valueOf(newQuantity));
            holder.cartItemPrice.setText(formatPrice(item.getPrice() * newQuantity));
        });

        // Event: giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            int item_position = holder.getAdapterPosition();
            if (item_position == RecyclerView.NO_POSITION) return;

            CartItem item_cart = mCartItems.get(item_position);
            int currentQuantity = item.getQuantity();

            if (currentQuantity > 1) {
                int newQuantity = currentQuantity - 1;
                item_cart.setQuantity(newQuantity);

                // Cập nhật database
                if (cartManager != null) {
                    cartManager.updateQuantity(item.getId(), newQuantity);
                }

                // Cập nhật giao diện
                notifyItemChanged(item_position);
            } else {
                // Nếu giảm về 0 thì xóa
                if (cartManager != null) {
                    cartManager.updateQuantity(item_cart.getId(), 0);
                }

                mCartItems.remove(item_position);
                notifyItemRemoved(item_position);
                notifyItemRangeChanged(item_position, mCartItems.size());
            }
        });


        // Event: icon trash - xóa item
        holder.trashIcon.setOnClickListener(v -> {
            mCartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mCartItems.size());
        });
    }
    @Override
    public int getItemCount() {
        if(mCartItems != null) return mCartItems.size();
        return 0;
    }
    private String formatPrice(double price) {
        return String.format("%,.0f₫", price);  // 1000000 -> 1.000.000₫
    }

}
