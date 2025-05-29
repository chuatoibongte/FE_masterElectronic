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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mater_electronic.R;
import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.database.cart.CartManager;
import com.example.mater_electronic.databinding.ActivityCheckoutBinding;
import com.example.mater_electronic.databinding.ItemCheckoutProductBinding;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.Address;
import com.example.mater_electronic.models.cart.CartItem;
import com.example.mater_electronic.models.checkout.CheckoutItem;
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
        checkoutViewModel.getResultMessage().observe(this, message -> {
            if (message != null) {
                // gọi API lưu order và chuyển hướng sang ActivitySuccess
                // Tạo intent để mở activity khác
                // Xác định lựa chọn Có/Không muốn nhận thông báo
                if(message == "Tạo đơn hàng thành công") {
                    cartManager.deleteSelectedItems();
                    int selectedId = binding.notificationChoiceGroup.getCheckedRadioButtonId();
                    boolean wantsNotification = selectedId == R.id.rbNotifyYes;
                    Intent intent = new Intent(ActivityCheckout.this, ActivitySuccess.class);
                    intent.putExtra("success", true);
                    intent.putExtra("wants_notification", wantsNotification);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityCheckout.this, ActivitySuccess.class);
                    intent.putExtra("success", true);
                    intent.putExtra("wants_notification", false);
                    startActivity(intent);
                }
            }
        });
        checkoutViewModel.getErrorMessage().observe(this, message -> {
            if(message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
        // Nhận dữ liệu từ Intent

        setupCustomerInfo();      // Thiết lập thông tin người nhận (hardcoded ở đây)
        showProductList();        // Hiển thị danh sách sản phẩm và tính tổng
        showTotalPrice();         // Set tổng giá
        setDeliveryDate();        // Set ngày giao hàng dự kiến
        binding.editAddressIcon.setOnClickListener(v -> showAddressDropdown());

        binding.btnBuyNow.setOnClickListener(v -> {
            // Kiểm tra có chọn địa chỉ hay chưa
            if(binding.recipientName.getText().toString().equals("Vui lòng chọn địa chỉ nhận hàng")) {
                Toast.makeText(this, "Vui lòng chọn địa chỉ nhận hàng", Toast.LENGTH_SHORT).show();
                return;
            }
            // Kiểm tra có chọn phương thức thanh toán hay chưa
            if(binding.paymentMethodGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }
            // Lấy địa chỉ đã chọn
            String selectedAddressName = binding.recipientName.getText().toString();
            String selectedAddressAddress = binding.recipientAddress.getText().toString();
            String selectedAddressPhone = binding.recipientPhone.getText().toString();
            Address selecedAddress = new Address(selectedAddressName, selectedAddressAddress, selectedAddressPhone);
            // Lấy phương thức thanh toán đã chọn
            RadioButton selectedPaymentMethodRadioButton = findViewById(binding.paymentMethodGroup.getCheckedRadioButtonId());
            String selectedPaymentMethod = "";
            if(selectedPaymentMethodRadioButton.getText().toString().equals("Thanh toán khi nhận hàng")) {
                selectedPaymentMethod = "direct";
            } else if(selectedPaymentMethodRadioButton.getText().toString().equals("Momo")) {
                selectedPaymentMethod = "momo";
            }
            else {
                selectedPaymentMethod = "banking";
            }
            // Lấy ghi chú
            String note = binding.etNote.getText().toString();
            // Lấy list electronics
            List<CheckoutItem> checkoutItems = new ArrayList<>();
            for (CartItem item : cartItems) {
                CheckoutItem checkoutItem = new CheckoutItem();
                checkoutItem.setElectronicID(item.getProductId());
                checkoutItem.setQuantity(item.getQuantity());
                checkoutItems.add(checkoutItem);
            }
            // Lấy userID
            String userID = getCurrentUserId();
            String accessToken = getCurrentUserAccessToken();
            // Gọi API để tạo đơn hàng
            checkoutViewModel.createOrder(accessToken, note, selectedPaymentMethod, selecedAddress, checkoutItems);
        });

    }
    private String getCurrentUserId() {
        SharedPreferences prefs = this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("_id", "");
    }
    private String getCurrentUserAccessToken() {
        SharedPreferences prefs = getApplication().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("accessToken", null);
    }
    private String formatPrice(double price) {
        return String.format("%,.0f₫", price);  // 1000000 -> 1.000.000₫
    }
    private void setupCustomerInfo() {
        binding.recipientName.setText("Vui lòng chọn địa chỉ nhận hàng");
        binding.recipientAddress.setText("");
        binding.recipientPhone.setText("");
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



    private void showAddressDropdown() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_address, null);
        dialog.setContentView(view);

        LinearLayout addressContainer = view.findViewById(R.id.addressContainer);
        //Lấy _id từ sharedPreferences
        String _id = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("_id", null);
        //Lấy dữ liệu từ account và hiển thị
        Account account = AccountDatabase.getInstance(this).accountDAO().getAccountById(_id);
        List<Address> addressList = account.getAddressList();
        for(Address address : addressList) {
            TextView addressView = createAddressTextView(address.getName() + " - " + address.getAddress() + " - " + address.getPhone());
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
        // Dữ liệu giả lập: Tên - Địa chỉ - SĐT
//        List<String> addresses = new ArrayList<>();
//        addresses.add("Nguyễn Văn A - 123 Lý Thường Kiệt - 0909123456");
//        addresses.add("Trần Thị B - 456 Điện Biên Phủ - 0909234567");
//        addresses.add("Phạm Văn C - 789 Cách Mạng Tháng 8 - 0909345678");
//
//        // Thêm địa chỉ từ danh sách
//        for (String address : addresses) {
//            TextView addressView = createAddressTextView(address);
//            addressView.setOnClickListener(v -> {
//                String selected = ((TextView) v).getText().toString();
//                String[] parts = selected.split(" - ");
//                if (parts.length == 3) {
//                    binding.recipientName.setText(parts[0]);
//                    binding.recipientAddress.setText(parts[1]);
//                    binding.recipientPhone.setText(parts[2]);
//                }
//                dialog.dismiss();
//            });
//            addressContainer.addView(addressView);
//        }

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
