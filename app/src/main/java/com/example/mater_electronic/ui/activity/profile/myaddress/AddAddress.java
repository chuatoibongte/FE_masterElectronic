package com.example.mater_electronic.ui.activity.profile.myaddress;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.databinding.ActivityAddAddressBinding;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.Address;
import com.example.mater_electronic.viewmodels.AccountViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddAddress extends AppCompatActivity {
    private ActivityAddAddressBinding binding;
    private String accountId;
    private AccountViewModel accountViewModel;
    private String accessToken;
    private Account currentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Tạo account view model
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        // Get account ID from SharedPreferences
        accountId = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("_id", null);

        //Get accessToken
        accessToken = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("accessToken", null);

        // Load account data
        loadAccountData();

        // Set click listeners
        setClickListeners();

        // Observe update results
        accountViewModel.getUpdateSuccess().observe(this, success -> {
            if (success != null) {
                if (success) {
                    Toast.makeText(this, "Thêm địa chỉ mới thành công", Toast.LENGTH_SHORT).show();
                    finish(); // Only finish when API call is successful
                } else {
                    Toast.makeText(this, "Cập nhật địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Observe error messages
        accountViewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAccountData() {
        if (accountId != null) {
            currentAccount = AccountDatabase.getInstance(this).accountDAO().getAccountById(accountId);
        }
    }

    private void setClickListeners() {
        // Back button
        binding.backArrow.setOnClickListener(v -> finish());

        // Agree/Save button
        binding.agreeBtn.setOnClickListener(v -> addNewAddress());
    }

    private void addNewAddress() {
        // Get input values
        String name = binding.nameEdt.getText().toString().trim();
        String address = binding.addressEdt.getText().toString().trim();
        String phone = binding.phoneEdt.getText().toString().trim();

        // Validate inputs
        if (!validateInputs(name, address, phone)) {
            return;
        }

        // Create new address object
        Address newAddress = new Address(name, address, phone);

        // Add the address to the list
        addAddressToAccount(newAddress);
    }

    private boolean validateInputs(String name, String address, String phone) {
        if (TextUtils.isEmpty(name)) {
            binding.nameEdt.setError("Vui lòng nhập họ và tên");
            binding.nameEdt.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(address)) {
            binding.addressEdt.setError("Vui lòng nhập địa chỉ");
            binding.addressEdt.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            binding.phoneEdt.setError("Vui lòng nhập số điện thoại");
            binding.phoneEdt.requestFocus();
            return false;
        }

        // Basic phone validation (10-11 digits)
        if (!phone.matches("^[0-9]{10,11}$")) {
            binding.phoneEdt.setError("Số điện thoại không hợp lệ (10-11 chữ số)");
            binding.phoneEdt.requestFocus();
            return false;
        }

        return true;
    }

    private void addAddressToAccount(Address newAddress) {
        if (currentAccount == null) {
            Toast.makeText(this, "Không thể lấy thông tin tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Address> addressList = currentAccount.getAddressList();
        if (addressList == null) {
            addressList = new ArrayList<>();
        }

        // Add new address to the list
        addressList.add(newAddress);

        // Update the account with new address list
        currentAccount.setAddressList(addressList);

        // Save to database
        try {
            AccountDatabase.getInstance(this).accountDAO().updateAccount(currentAccount);
            accountViewModel.updateAddress(accessToken, addressList);
            // Return to previous activity
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi lưu địa chỉ: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}