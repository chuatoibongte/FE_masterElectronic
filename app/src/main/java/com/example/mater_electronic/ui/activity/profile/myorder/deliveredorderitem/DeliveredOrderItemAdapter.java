package com.example.mater_electronic.ui.activity.profile.myorder.deliveredorderitem;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.models.myorder.OrderElectronics;
import com.example.mater_electronic.ui.activity.detail.ProductDetailActivity;
import com.example.mater_electronic.ui.activity.review.ReviewActivity;
import com.example.mater_electronic.utils.FormatUtil;
import com.example.mater_electronic.utils.LoadImageByUrl;

import java.util.List;

public class DeliveredOrderItemAdapter extends RecyclerView.Adapter<DeliveredOrderItemAdapter.DeliveredOrderItemViewHolder> {

    List<OrderElectronics> orderElectronicsList;

    public DeliveredOrderItemAdapter(List<OrderElectronics> orderElectronicsList) {
        super();
        this.orderElectronicsList = orderElectronicsList;
    }

    @NonNull
    @Override
    public DeliveredOrderItemAdapter.DeliveredOrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_electronic_item, parent, false);
        return new DeliveredOrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredOrderItemAdapter.DeliveredOrderItemViewHolder holder, int position) {
        OrderElectronics orderElectronics = orderElectronicsList.get(position);
        LoadImageByUrl.loadImage(holder.ivElectronicItem, orderElectronics.getElectronicID().getElectronicImgs().get(0).getUrl());
        holder.tvProductName.setText(orderElectronics.getElectronicID().getName());
        holder.tvProductName.setOnClickListener(v -> {
            Log.e("Adapter", "Click item id: " + orderElectronics.getElectronicID().get_id());
            Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
            intent.putExtra("product_id", orderElectronics.getElectronicID().get_id());
            v.getContext().startActivity(intent);
        });
        holder.tvProductPrice.setText(FormatUtil.formatPrice(orderElectronics.getElectronicID().getPrice()));
        holder.tvProductQuantity.setText("x" + String.valueOf(orderElectronics.getQuantity()));
        holder.tvTotalPrice.setText(FormatUtil.formatPrice(orderElectronics.getElectronicID().getPrice() * orderElectronics.getQuantity()));
        holder.btnReview.setOnClickListener(v-> {
            String productId = orderElectronics.getElectronicID().get_id();
            String productName = orderElectronics.getElectronicID().getName();
            String productImageUrl = orderElectronics.getElectronicID().getElectronicImgs().get(0).getUrl();
            Intent intent = new Intent(v.getContext(), ReviewActivity.class);
            intent.putExtra("electronicID", productId);
            intent.putExtra("productName", productName);
            intent.putExtra("productImageUrl", productImageUrl);
            v.getContext().startActivity(intent);
        });
        Log.d("Adapter", "Binding item: " + orderElectronics.getElectronicID().getName());
    }

    @Override
    public int getItemCount() {
        return orderElectronicsList != null ? orderElectronicsList.size() : 0;
    }

    public static class DeliveredOrderItemViewHolder extends RecyclerView.ViewHolder {
        ImageView ivElectronicItem;
        TextView tvProductName;
        TextView tvProductQuantity;
        TextView tvProductPrice;
        TextView tvSecondProductQuantity;
        TextView tvThirdProduct;
        TextView tvThirdProductQuantity;
        TextView tvTotalPrice;
        Button btnReview;
        public DeliveredOrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivElectronicItem = itemView.findViewById(R.id.ivElectronicItem);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvSecondProductQuantity = itemView.findViewById(R.id.tvSecondProductQuantity);
            tvThirdProduct = itemView.findViewById(R.id.tvThirdProduct);
            tvThirdProductQuantity = itemView.findViewById(R.id.tvThirdProductQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            btnReview = itemView.findViewById(R.id.btnReview);
        }
    }
}
