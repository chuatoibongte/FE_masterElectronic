package com.example.mater_electronic.ui.activity.checkout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mater_electronic.R;
import com.example.mater_electronic.database.cart.CartManager;
import com.example.mater_electronic.databinding.ActivityCheckoutBinding;
import com.example.mater_electronic.databinding.ItemCheckoutProductBinding;
import com.example.mater_electronic.models.cart.CartItem;
import com.example.mater_electronic.ui.activity.success.ActivitySuccess;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityCheckout extends AppCompatActivity {
    private CartManager cartManager;
    private CheckoutViewModel checkoutViewModel;

    private ActivityCheckoutBinding binding;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartManager = new CartManager(this, getCurrentUserId());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.lvProductList.setLayoutManager(linearLayoutManager);

        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);
        checkoutViewModel.getCartItems().observe(this, items -> {
            cartItems = new ArrayList<>(items);
            binding.lvProductList.setAdapter(new CheckoutProductAdapter(cartItems));
            showProductList();
            showTotalPrice();
        });
        // Nhận dữ liệu từ Intent

        setupCustomerInfo();      // Thiết lập thông tin người nhận (hardcoded ở đây)
        showProductList();        // Hiển thị danh sách sản phẩm và tính tổng
        showTotalPrice();         // Set tổng giá
        setDeliveryDate();        // Set ngày giao hàng dự kiến
        setupClickListeners();    // Xử lý nút bấm
        binding.editAddressIcon.setOnClickListener(v -> showAddressDropdown());

        binding.checkoutMuangayButton.setOnClickListener(v -> {
            // Xác định lựa chọn Có/Không
            int selectedId = binding.notificationChoiceGroup.getCheckedRadioButtonId();
            boolean wantsNotification = selectedId == R.id.rbNotifyYes;

            // Tạo intent để mở activity khác
            Intent intent = new Intent(ActivityCheckout.this, ActivitySuccess.class); // <-- thay bằng tên Activity thật
            intent.putExtra("wants_notification", wantsNotification);
            startActivity(intent);
        });

    }
    private String getCurrentUserId() {
        SharedPreferences prefs = this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("_id", "");
    }
    private String formatPrice(double price) {
        return String.format("%,.0f₫", price);  // 1000000 -> 1.000.000₫
    }
    private void setupCustomerInfo() {
        binding.recipientName.setText("Nguyễn Văn A");
        binding.recipientAddress.setText("123 Lý Thường Kiệt, Q.10, TP.HCM");
        binding.recipientPhone.setText("0901234567");
    }

    private void showProductList() {
        // SET ADAPTER cho lvProductList
    }

    private void showTotalPrice() {
        binding.txtTotalPrice.setText("Tổng giá: " + String.valueOf(formatPrice(cartManager.getTotalPrice())));
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

    private void showAddressDropdown() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_address, null);
        dialog.setContentView(view);

        LinearLayout addressContainer = view.findViewById(R.id.addressContainer);

        // Dữ liệu giả lập: Tên - Địa chỉ - SĐT
        List<String> addresses = new ArrayList<>();
        addresses.add("Nguyễn Văn A - 123 Lý Thường Kiệt - 0909123456");
        addresses.add("Trần Thị B - 456 Điện Biên Phủ - 0909234567");
        addresses.add("Phạm Văn C - 789 Cách Mạng Tháng 8 - 0909345678");

        // Thêm địa chỉ từ danh sách
        for (String address : addresses) {
            TextView addressView = createAddressTextView(address);
            addressView.setOnClickListener(v -> {
                String selected = ((TextView) v).getText().toString();
                String[] parts = selected.split(" - ");
                if (parts.length == 3) {
                    binding.recipientName.setText(parts[0]);
                    binding.recipientAddress.setText(parts[1]);
                    binding.recipientPhone.setText(parts[2]);
                }
                dialog.dismiss();
            });
            addressContainer.addView(addressView);
        }

        // Dòng "Thêm địa chỉ"
        TextView addAddressView = createAddressTextView("➕ Thêm địa chỉ mới");
        addAddressView.setTypeface(null, Typeface.BOLD);
        addAddressView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark));
        addAddressView.setOnClickListener(v -> {
            // TODO: Hiển thị form nhập địa chỉ mới hoặc mở activity khác
            dialog.dismiss();
        });
        addressContainer.addView(addAddressView);

        dialog.show();
    }


    private TextView createAddressTextView(String text) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tv.setPadding(20, 24, 20, 24);
        tv.setText(text);
        tv.setTextColor(Color.BLACK);
        tv.setBackgroundResource(android.R.drawable.list_selector_background);
        return tv;
    }


}
