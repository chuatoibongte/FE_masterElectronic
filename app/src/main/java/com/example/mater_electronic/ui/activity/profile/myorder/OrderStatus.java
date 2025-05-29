package com.example.mater_electronic.ui.activity.profile.myorder;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mater_electronic.R;

public class OrderStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        String orderId = getIntent().getStringExtra("orderId");
        String status = getIntent().getStringExtra("status");
        String time = getIntent().getStringExtra("time");
        TextView tvOrderId = findViewById(R.id.orderid);
        TextView tvStatus = findViewById(R.id.txt_order_status);
        TextView tvOrderTime = findViewById(R.id.tvOrderTime);
        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> finish());
        tvOrderId.setText("Mã Đơn Hàng: " + orderId);
        tvOrderTime.setText(time);
        if(status.equals("pending")) {
            TextView tvPending = findViewById(R.id.tvPending);
            tvPending.setBackgroundResource(R.drawable.button_background2);
        }
        if(status.equals("confirmed")) {
            TextView tvConfirmed = findViewById(R.id.tvConfirmed);
            tvConfirmed.setBackgroundResource(R.drawable.button_background2);
        }
    }
}