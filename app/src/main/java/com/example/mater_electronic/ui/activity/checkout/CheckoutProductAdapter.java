// CheckoutProductAdapter.java
package com.example.mater_electronic.ui.activity.checkout;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.databinding.ItemCheckoutProductBinding;
import com.example.mater_electronic.models.cart.CartItem;

import java.util.ArrayList;

public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.ProductViewHolder> {

    private final ArrayList<CartItem> cartItems;

    public CheckoutProductAdapter(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCheckoutProductBinding binding = ItemCheckoutProductBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.binding.productName.setText(item.getName());
        holder.binding.productQuantity.setText("x" + item.getQuantity());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ItemCheckoutProductBinding binding;

        public ProductViewHolder(ItemCheckoutProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
