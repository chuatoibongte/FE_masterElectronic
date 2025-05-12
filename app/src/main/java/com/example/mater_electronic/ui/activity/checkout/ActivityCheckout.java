package com.example.mater_electronic.ui.activity.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;


import androidx.appcompat.app.AppCompatActivity;

import com.example.mater_electronic.databinding.ActivityCheckoutBinding;
import com.example.mater_electronic.databinding.ItemCheckoutProductBinding;
import com.example.mater_electronic.models.cart.CartItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ActivityCheckout extends AppCompatActivity {

    private ActivityCheckoutBinding binding;
    private ArrayList<CartItem> cartItems;
    private double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanaState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nhận dữ liệu từ Intent
        cartItems = (ArrayList<CartItem>) getIntent().getSerializableExtra("cart_list");

        setupCustomerInfo();      // Thiết lập thông tin người nhận (hardcoded ở đây)
        showProductList();        // Hiển thị danh sách sản phẩm và tính tổng
        showTotalPrice();         // Set tổng giá
        setDeliveryDate();        // Set ngày giao hàng dự kiến
        setupClickListeners();    // Xử lý nút bấm
    }

    private void setupCustomerInfo() {
        binding.recipientName.setText("Nguyễn Văn A");
        binding.recipientAddress.setText("123 Lý Thường Kiệt, Q.10, TP.HCM");
        binding.recipientPhone.setText("0901234567");
    }

    private void showProductList() {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (CartItem item : cartItems) {
            ItemCheckoutProductBinding itemBinding = ItemCheckoutProductBinding.inflate(inflater);
            itemBinding.productName.setText(item.getName());
            itemBinding.productQuantity.setText("x" + item.getQuantity());

            double itemTotal = item.getPrice() * item.getQuantity();
            totalPrice += itemTotal;

            binding.productListLayout.addView(itemBinding.getRoot());
        }
    }

    private void showTotalPrice() {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        binding.txtTotalPrice.setText(format.format(totalPrice));
    }

    private void setDeliveryDate() {
        binding.txtDeliveryDate.setText("Dự Kiến: 15/05/2025");
    }

    private void setupClickListeners() {
        binding.checkoutMuangayButton.setOnClickListener(v -> {
            // Xử lý thanh toán ở đây
            // TODO: xử lý logic thanh toán
        });

        binding.editAddressIcon.setOnClickListener(v -> {
            // TODO: mở dialog chỉnh sửa địa chỉ nếu cần
        });
    }
}
