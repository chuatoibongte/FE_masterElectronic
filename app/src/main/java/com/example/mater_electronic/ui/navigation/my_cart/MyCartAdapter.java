package com.example.mater_electronic.ui.navigation.my_cart;

import android.content.Context;
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
import com.example.mater_electronic.models.cart.CartItem;
import com.example.mater_electronic.ui.navigation.home.HomeProductAdapter;

import java.text.BreakIterator;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {

    private Context mContext;
    private List<CartItem> mCartItems;
    public MyCartAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<CartItem> cartItems) {
        this.mCartItems = cartItems;
        notifyDataSetChanged();
    }
    public static class MyCartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImg;
        TextView cartItemName;
        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImg = itemView.findViewById(R.id.cartItemImg);
            cartItemName = itemView.findViewById(R.id.cartItemName);
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
        if(item == null) return;
        //TODO: bind data to view
        Glide.with(holder.itemView.getContext())
                .load(item.getImg())  // Đây là link ảnh từ Cloudinary
                .placeholder(R.drawable.processing) // ảnh mặc định khi đang tải
                .error(R.drawable.processing)             // ảnh hiển thị khi lỗi tải ảnh
                .into(holder.cartItemImg);
        holder.cartItemName.setText(item.getName());
        //TODO: set event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            // go to detail
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Clicked" + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        //TODO: set animation
    }

    @Override
    public int getItemCount() {
        if(mCartItems != null) return mCartItems.size();
        return 0;
    }
}
