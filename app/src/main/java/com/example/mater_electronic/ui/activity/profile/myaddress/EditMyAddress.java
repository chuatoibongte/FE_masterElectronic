package com.example.mater_electronic.ui.activity.profile.myaddress;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.databinding.ActivityEditMyAddressBinding;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.Address;
import com.example.mater_electronic.viewmodels.AccountViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditMyAddress extends AppCompatActivity {
    private ActivityEditMyAddressBinding binding;
    private int addressPosition = -1;
    private String accountId;
    private Account currentAccount;
    private AccountViewModel accountViewModel;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditMyAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Tạo account view model
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        // Get data from intent
        getIntentData();

        // Get account data
        loadAccountData();

        // Set up UI
        setupUI();

        // Set click listeners
        setClickListeners();

        // Observe update results
        accountViewModel.getUpdateSuccess().observe(this, success -> {
            if (success != null) {
                if (success) {
                    if (addressPosition != -1) {
                        Toast.makeText(this, "Cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Thêm địa chỉ mới thành công", Toast.LENGTH_SHORT).show();
                    }
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

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            addressPosition = intent.getIntExtra("address_position", -1);

            // Get existing address data for editing
            if (addressPosition != -1) {
                String name = intent.getStringExtra("address_name");
                String address = intent.getStringExtra("address_address");
                String phone = intent.getStringExtra("address_phone");

                // Pre-fill the form with existing data
                binding.nameEdt.setText(name);
                binding.addressEdt.setText(address);
                binding.phoneEdt.setText(phone);

                //Fill the card address data
                binding.tvAddressUserName.setText(name);
                binding.tvAddress.setText(address);
                binding.tvPhone.setText(phone);
            }
        }

        // Get account ID from SharedPreferences
        accountId = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("_id", null);
        //Lấy accessToken từ sharedPreferences
        accessToken = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("accessToken", null);
    }

    private void loadAccountData() {
        if (accountId != null) {
            currentAccount = AccountDatabase.getInstance(this).accountDAO().getAccountById(accountId);
        }
    }

    private void setupUI() {
        // Fix input types - they shouldn't be password type for address fields
        binding.nameEdt.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        binding.addressEdt.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        binding.phoneEdt.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
    }

    private void setClickListeners() {
        // Back button
        binding.backArrow.setOnClickListener(v -> finish());

        // Agree/Save button
        binding.agreeBtn.setOnClickListener(v -> saveAddress());
    }

    private void saveAddress() {
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

        // Update the address list
        updateAddressList(newAddress);
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

        // Basic phone validation
        if (!phone.matches("^[0-9]{10,11}$")) {
            binding.phoneEdt.setError("Số điện thoại không hợp lệ");
            binding.phoneEdt.requestFocus();
            return false;
        }

        return true;
    }

    private void updateAddressList(Address newAddress) {
        if (currentAccount == null) {
            Toast.makeText(this, "Không thể lấy thông tin tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Address> addressList = currentAccount.getAddressList();
        if (addressList == null) {
            addressList = new ArrayList<>();
        }

        if (addressPosition != -1 && addressPosition < addressList.size()) {
            // Update existing address
            addressList.set(addressPosition, newAddress);
        } else {
            // Add new address
            addressList.add(newAddress);
        }

        // Update the account with new address list
        currentAccount.setAddressList(addressList);

        try {
            // First update local database
            AccountDatabase.getInstance(this).accountDAO().updateAccount(currentAccount);

            // Then call API to update backend - DON'T call finish() here
            accountViewModel.updateAddress(accessToken, addressList);

            // The finish() will be called in the observer when API call succeeds

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