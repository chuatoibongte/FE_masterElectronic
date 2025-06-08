package com.example.mater_electronic.ui.activity.profile.myorder.deliveredorderitem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivityDeliveredOrderItemBinding;

import java.util.ArrayList;

public class DeliveredOrderItem extends AppCompatActivity {
    private ActivityDeliveredOrderItemBinding binding;

    private DeliveredOrderItemAdapter deliveredOrderItemAdapter;
    private DeliveredOrderItemViewModel deliveredOrderItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveredOrderItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String orderId = getIntent().getStringExtra("orderId");
        String status = getIntent().getStringExtra("status");
        String time = getIntent().getStringExtra("time");

        binding.backBtn.setOnClickListener(v -> finish());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvOrderItem.setLayoutManager(linearLayoutManager);

        deliveredOrderItemViewModel = new ViewModelProvider(this).get(DeliveredOrderItemViewModel.class);

        deliveredOrderItemViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading != null) {
                binding.loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                binding.rvOrderItem.setVisibility(isLoading ? View.GONE : View.VISIBLE);
            }
        });

        deliveredOrderItemViewModel.getElectronicsByOrderId(getCurrentUserAccessToken(), orderId);

        deliveredOrderItemViewModel.getOrderElectronicsList().observe(this, orderElectronics -> {
            Log.d("DeliveredOrderItem", "orderElectronics size: " + orderElectronics.size());
            if(orderElectronics == null) {
                binding.tvNoOrder.setVisibility(View.VISIBLE);
                deliveredOrderItemAdapter = new DeliveredOrderItemAdapter(new ArrayList<>());
                binding.rvOrderItem.setAdapter(deliveredOrderItemAdapter);
            }
            else {
                binding.tvNoOrder.setVisibility(View.INVISIBLE);
                deliveredOrderItemAdapter = new DeliveredOrderItemAdapter(orderElectronics);
                binding.rvOrderItem.setAdapter(deliveredOrderItemAdapter);
            }
        });
//        deliveredOrderItemViewModel.getIsLoading().observe(this, isLoading -> {
//            if(isLoading != null) {
//                binding.loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
//                binding.rvOrderItem.setVisibility(isLoading ? View.GONE : View.VISIBLE);
//            }
//        });
        deliveredOrderItemViewModel.getErrorMessage().observe(this, errorMessage -> {
            Log.e("Error", errorMessage);
            binding.tvNoOrder.setVisibility(View.VISIBLE);
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