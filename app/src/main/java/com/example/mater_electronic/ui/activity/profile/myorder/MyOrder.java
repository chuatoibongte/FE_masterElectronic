package com.example.mater_electronic.ui.activity.profile.myorder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivityMyOrderBinding;

import java.util.ArrayList;

public class MyOrder extends AppCompatActivity {
    private String status = "pending";
    MyOrderViewModel myOrderViewModel;
    MyOrderAdapter myOrderAdapter;
    private ActivityMyOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvMyOrder.setLayoutManager(linearLayoutManager);

        myOrderViewModel = new ViewModelProvider(this).get(MyOrderViewModel.class);
        myOrderViewModel.getOrderByUserIDandStatus(getCurrentUserAccessToken(), status);
        myOrderViewModel.getOrderByUserIDandStatus().observe(this, orders -> {
            if(orders == null) {
                binding.tvNoOrder.setVisibility(View.VISIBLE);
                myOrderAdapter = new MyOrderAdapter(new ArrayList<>(), new MyOrderActionListener() {
                    @Override
                    public void onCancelOrder(String orderId) {
                        myOrderViewModel.cancelOrder(getCurrentUserAccessToken(), orderId);
                    }
                    @Override
                    public void onConfirmReceived(String orderId) {
                        myOrderViewModel.receivedOrder(getCurrentUserAccessToken(), orderId);
                    }
                });
                binding.rvMyOrder.setAdapter(myOrderAdapter);
            }
            else {
                binding.tvNoOrder.setVisibility(View.INVISIBLE);
                Log.e("MyOrder", "Số lượng order nhận được: " + (orders != null ? orders.size() : "null"));
                myOrderAdapter = new MyOrderAdapter(orders, new MyOrderActionListener() {
                    @Override
                    public void onCancelOrder(String orderId) {
                        myOrderViewModel.cancelOrder(getCurrentUserAccessToken(), orderId);
                    }
                    @Override
                    public void onConfirmReceived(String orderId) {
                        myOrderViewModel.receivedOrder(getCurrentUserAccessToken(), orderId);
                    }
                });
                binding.rvMyOrder.setAdapter(myOrderAdapter);
            }
        });
//        myOrderViewModel.getIsCancelOrderCalled().observe(this, isCancelOrderCalled -> {
//            if(isCancelOrderCalled != null) {
//                myOrderViewModel.getOrderByUserIDandStatus(getCurrentUserAccessToken(), "pending");
//            }
//        });
//        myOrderViewModel.getIsReceivedOrderCalled().observe(this, isReceivedOrderCalled -> {
//            if(isReceivedOrderCalled != null) {
//                binding.deliveredStatus.performClick();
//                // myOrderViewModel.getOrderByUserIDandStatus(getCurrentUserAccessToken(), "delivered");
//            }
//        });
        myOrderViewModel.getErrorMessage().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
        myOrderViewModel.getIsLoading().observe(this, isLoading -> {
            Log.e("Loading", "Loading: " + isLoading);
            if(isLoading != null) {
                if (isLoading) {
                    binding.rvMyOrder.setVisibility(View.GONE);
                    binding.loadingOverlay.setVisibility(View.VISIBLE);
                } else {
                    binding.rvMyOrder.setVisibility(View.VISIBLE);
                    binding.loadingOverlay.setVisibility(View.GONE);
                }
            }
        });
        binding.backBtn.setOnClickListener(v -> finish());
        binding.pendingStatus.setOnClickListener(v -> {
            status = "pending";
            myOrderViewModel.getOrderByUserIDandStatus(getCurrentUserAccessToken(), status);
            binding.pendingStatus.setBackgroundResource(R.drawable.button_background2);
            binding.processingStatus.setBackgroundResource(0);
            binding.confirmedStatus.setBackgroundResource(0);
            binding.intransitStatus.setBackgroundResource(0);
            binding.deliveredStatus.setBackgroundResource(0);
            binding.canceledStatus.setBackgroundResource(0);
            binding.rejectedStatus.setBackgroundResource(0);
        });
        binding.processingStatus.setOnClickListener(v -> {
            status = "processing";
            myOrderViewModel.getOrderByUserIDandStatus(getCurrentUserAccessToken(), status);
            binding.pendingStatus.setBackgroundResource(0);
            binding.processingStatus.setBackgroundResource(R.drawable.button_background2);
            binding.confirmedStatus.setBackgroundResource(0);
            binding.intransitStatus.setBackgroundResource(0);
            binding.deliveredStatus.setBackgroundResource(0);
            binding.canceledStatus.setBackgroundResource(0);
            binding.rejectedStatus.setBackgroundResource(0);
        });
        binding.confirmedStatus.setOnClickListener(v -> {
            status = "confirmed";
            myOrderViewModel.getOrderByUserIDandStatus(getCurrentUserAccessToken(), status);
            binding.pendingStatus.setBackgroundResource(0);
            binding.processingStatus.setBackgroundResource(0);
            binding.confirmedStatus.setBackgroundResource(R.drawable.button_background2);
            binding.intransitStatus.setBackgroundResource(0);
            binding.deliveredStatus.setBackgroundResource(0);
            binding.canceledStatus.setBackgroundResource(0);
            binding.rejectedStatus.setBackgroundResource(0);
        });
        binding.intransitStatus.setOnClickListener(v -> {
            status = "in transit";
            myOrderViewModel.getOrderByUserIDandStatus(getCurrentUserAccessToken(), status);
            binding.pendingStatus.setBackgroundResource(0);
            binding.processingStatus.setBackgroundResource(0);
            binding.confirmedStatus.setBackgroundResource(0);
            binding.intransitStatus.setBackgroundResource(R.drawable.button_background2);
            binding.deliveredStatus.setBackgroundResource(0);
            binding.canceledStatus.setBackgroundResource(0);
            binding.rejectedStatus.setBackgroundResource(0);
        });
        binding.deliveredStatus.setOnClickListener(v -> {
            status = "delivered";
            myOrderViewModel.getOrderByUserIDandStatus(getCurrentUserAccessToken(), status);
            binding.pendingStatus.setBackgroundResource(0);
            binding.processingStatus.setBackgroundResource(0);
            binding.confirmedStatus.setBackgroundResource(0);
            binding.intransitStatus.setBackgroundResource(0);
            binding.deliveredStatus.setBackgroundResource(R.drawable.button_background2);
            binding.canceledStatus.setBackgroundResource(0);
            binding.rejectedStatus.setBackgroundResource(0);
        });
        binding.canceledStatus.setOnClickListener(v -> {
            status = "canceled";
            myOrderViewModel.getOrderByUserIDandStatus(getCurrentUserAccessToken(), status);
            binding.pendingStatus.setBackgroundResource(0);
            binding.processingStatus.setBackgroundResource(0);
            binding.confirmedStatus.setBackgroundResource(0);
            binding.intransitStatus.setBackgroundResource(0);
            binding.deliveredStatus.setBackgroundResource(0);
            binding.canceledStatus.setBackgroundResource(R.drawable.button_background2);
            binding.rejectedStatus.setBackgroundResource(0);
        });
        binding.rejectedStatus.setOnClickListener(v -> {
            status = "rejected";
            myOrderViewModel.getOrderByUserIDandStatus(getCurrentUserAccessToken(), status);
            binding.pendingStatus.setBackgroundResource(0);
            binding.processingStatus.setBackgroundResource(0);
            binding.confirmedStatus.setBackgroundResource(0);
            binding.intransitStatus.setBackgroundResource(0);
            binding.deliveredStatus.setBackgroundResource(0);
            binding.canceledStatus.setBackgroundResource(0);
            binding.rejectedStatus.setBackgroundResource(R.drawable.button_background2);
        });
    }
    private String getCurrentUserId() {
        SharedPreferences prefs = this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("_id", "");
    }
    private String getCurrentUserAccessToken() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("accessToken", null);
    }
}