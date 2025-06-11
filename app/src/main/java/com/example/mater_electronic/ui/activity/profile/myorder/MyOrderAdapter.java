package com.example.mater_electronic.ui.activity.profile.myorder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.models.myorder.Order;
import com.example.mater_electronic.ui.activity.profile.myorder.deliveredorderitem.DeliveredOrderItem;
import com.example.mater_electronic.utils.FormatUtil;
import com.example.mater_electronic.utils.LoadImageByUrl;

import java.util.List;
import java.util.Objects;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder> {
    private List<Order> orderList;
    private MyOrderActionListener actionListener;
    public MyOrderAdapter(List<Order> orderList, MyOrderActionListener actionListener) {
        super();
        this.orderList = orderList;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public MyOrderAdapter.MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.MyOrderViewHolder holder, int position) {
        Order orderItem = orderList.get(position);
        holder.tvStatus.setText("Trạng thái: " + orderItem.getStatus());
        if(orderItem.getStatus().equals("in transit")) {
            holder.btnReceived.setVisibility(View.VISIBLE);
            holder.btnReceived.setOnClickListener(v -> {
                actionListener.onConfirmReceived(orderItem.get_id());
                Toast.makeText(v.getContext(), "Đã nhận được hàng", Toast.LENGTH_SHORT).show();
            });
        }
        if(orderItem.getStatus().equals("pending")) {
            holder.btnReceived.setVisibility(View.VISIBLE);
            holder.btnReceived.setText("Hủy đơn hàng");
            holder.btnReceived.setOnClickListener(v -> {
                actionListener.onCancelOrder(orderItem.get_id());
                Toast.makeText(v.getContext(), "Hủy đơn hàng", Toast.LENGTH_SHORT).show();
            });
        }
        holder.tvOrderDate.setText(orderItem.getTime().substring(0, 10));
        holder.tvFirstProduct.setText(orderItem.getListElectronics().get(0).getElectronicID().getName());
        holder.tvFirstProductQuantity.setText("x" + String.valueOf(orderItem.getListElectronics().get(0).getQuantity()));
        if(orderItem.getListElectronics().size() > 1) {
            holder.tvSecondProduct.setText(orderItem.getListElectronics().get(1).getElectronicID().getName());
            holder.tvSecondProductQuantity.setText("x" + String.valueOf(orderItem.getListElectronics().get(1).getQuantity()));
        }
        if(orderItem.getListElectronics().size() > 2) {
            holder.tvThirdProduct.setText(orderItem.getListElectronics().get(2).getElectronicID().getName());
            holder.tvThirdProductQuantity.setText("x" + String.valueOf(orderItem.getListElectronics().get(2).getQuantity()));
        }
        if(orderItem.getListElectronics().size() > 3) {
            holder.tvMore.setVisibility(View.VISIBLE);
        }
        LoadImageByUrl.loadImage(holder.ivOrderItem, orderItem.getListElectronics().get(0).getElectronicID().getElectronicImgs().get(0).getUrl());
        holder.tvTotalPrice.setText("Tổng giá: \n" + FormatUtil.formatPrice(orderItem.getTotalPrice()));

        if(Objects.equals(orderItem.getStatus(), "delivered")) {
            holder.cvOrderItem.setOnClickListener(v -> {
                String orderId = orderItem.get_id();
                String status = orderItem.getStatus();
                String time = orderItem.getCreatedAt().substring(0, 10);
                Intent intent = new Intent(v.getContext(), DeliveredOrderItem.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("status", status);
                intent.putExtra("time", time);
                v.getContext().startActivity(intent);
            });
        }
        else {
            holder.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String orderId = orderItem.get_id();
                    String status = orderItem.getStatus();
                    String time = orderItem.getCreatedAt().substring(0, 10);
                    Intent intent = new Intent(v.getContext(), OrderStatus.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("status", status);
                    intent.putExtra("time", time);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(orderList != null) return orderList.size();
        return 0;
    }
    public static class MyOrderViewHolder extends RecyclerView.ViewHolder {
        CardView cvOrderItem;
        LinearLayout llParent;
        TextView tvStatus;
        TextView tvOrderDate;
        TextView tvFirstProduct;
        TextView tvFirstProductQuantity;
        TextView tvSecondProduct;
        TextView tvSecondProductQuantity;
        TextView tvThirdProduct;
        TextView tvThirdProductQuantity;
        TextView tvTotalPrice;
        ImageView ivOrderItem;
        TextView tvMore;

        Button btnReceived;

        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            cvOrderItem = itemView.findViewById(R.id.cvOrderItem);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvFirstProduct = itemView.findViewById(R.id.tvFirstProduct);
            tvFirstProductQuantity = itemView.findViewById(R.id.tvFirstProductQuantity);
            tvSecondProduct = itemView.findViewById(R.id.tvSecondProduct);
            tvSecondProductQuantity = itemView.findViewById(R.id.tvSecondProductQuantity);
            tvThirdProduct = itemView.findViewById(R.id.tvThirdProduct);
            tvThirdProductQuantity = itemView.findViewById(R.id.tvThirdProductQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            ivOrderItem = itemView.findViewById(R.id.ivOrderItem);
            llParent = itemView.findViewById(R.id.llParent);
            tvMore = itemView.findViewById(R.id.tvMore);
            btnReceived = itemView.findViewById(R.id.btnReceived);
        }
    }
}
