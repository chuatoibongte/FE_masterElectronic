package com.example.mater_electronic.ui.activity.profile.myaddress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.databinding.ActivityMyAddressBinding;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.Address;
import com.example.mater_electronic.viewmodels.AccountViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyAddress extends AppCompatActivity implements AddressActionListener {
    public ActivityMyAddressBinding binding;
    private AddressAdapter addressAdapter;
    private String accountId;
    private AccountViewModel accountViewModel;
    private String accessToken;
    private List<Address> currentAddressList;
    private boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //back Button
        binding.backArrow.setOnClickListener(v -> finish());

        //Lấy _id từ sharedPreferences
        accountId = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("_id", null);
        accessToken = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("accessToken", null);

        setupViewModel();
        setupRecyclerView();
        observeViewModel();
        loadAndDisplayAddresses();

        //Sang AddAddress khi nhấn vào add button
        binding.addAddressBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAddress.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when returning from EditMyAddress
        loadAndDisplayAddresses();
    }

    private void setupViewModel() {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
    }

    private void observeViewModel() {
        // Observe update success for API calls
        accountViewModel.getUpdateSuccess().observe(this, success -> {
            if (success != null) {
                // Reset updating flag regardless of success or failure
                isUpdating = false;

                if (success) {
                    // Update local database after successful API call
                    updateLocalDatabase();
                    Toast.makeText(this, "Cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cập nhật địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                    // reload data to restore original state
                    loadAndDisplayAddresses();
                }
            }
        });

        // Observe result message
        accountViewModel.getResultMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // Observe error messages
        accountViewModel.getErrorMessage().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                isUpdating = false; // Reset flag to false
                Toast.makeText(this, "Lỗi: " + error, Toast.LENGTH_LONG).show();
                // Reload original data on error
                loadAndDisplayAddresses();
            }
        });
    }

    private void loadAndDisplayAddresses() {
        if (accountId == null) {
            Toast.makeText(this, "Không thể lấy thông tin tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }

        //Lấy dữ liệu từ account và hiển thị
        Account account = AccountDatabase.getInstance(this).accountDAO().getAccountById(accountId);

        if (account != null && account.getAddressList() != null && !account.getAddressList().isEmpty()) {
            currentAddressList = new ArrayList<>();
            for (Address addr : account.getAddressList()) {
                currentAddressList.add(addr);
            }
            displayAddresses(currentAddressList);
        } else {
            Toast.makeText(this, "Không có địa chỉ nào", Toast.LENGTH_SHORT).show();
            currentAddressList = new ArrayList<>();
            // Clear the adapter if no addresses
            if (addressAdapter != null) {
                addressAdapter.updateAddressList(new ArrayList<>());
            }
        }
    }

    private void setupRecyclerView() {
        binding.rvAddress.setLayoutManager(new LinearLayoutManager(this));
        addressAdapter = new AddressAdapter(new ArrayList<>(), this);
        binding.rvAddress.setAdapter(addressAdapter);
    }

    private void displayAddresses(List<Address> addressList) {
        if (addressAdapter != null) {
            addressAdapter.updateAddressList(addressList);
        }
    }

    @Override
    public void onDeleteAddress(int position) {
        // Validate position
        if (currentAddressList == null || position < 0 || position >= currentAddressList.size()) {
            Toast.makeText(this, "Lỗi: Vị trí không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        showDeleteConfirmationDialog(position);
    }

    @Override
    public void onEditAddress(int position, Address address) {
        Intent intent = new Intent(this, EditMyAddress.class);
        intent.putExtra("address_position", position);
        intent.putExtra("address_name", address.getName());
        intent.putExtra("address_address", address.getAddress());
        intent.putExtra("address_phone", address.getPhone());
        startActivity(intent);
    }

    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa địa chỉ này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    deleteAddress(position);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteAddress(int position) {
        if (isUpdating) {
            Toast.makeText(this, "Đang xử lý, vui lòng đợi...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentAddressList == null || position < 0 || position >= currentAddressList.size()) {
            Toast.makeText(this, "Lỗi: Không thể xóa địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Remove from current list
            currentAddressList.remove(position);

            // Update adapter immediately for better UX
            addressAdapter.removeItem(position);

            // Set updating flag
            isUpdating = true;


            // Call API to update address list if access token is available
            if (accessToken != null && !accessToken.isEmpty()) {
                accountViewModel.updateAddress(accessToken, new ArrayList<>(currentAddressList));
            }
        } catch (Exception e) {
            isUpdating = false;
            Toast.makeText(this, "Lỗi khi xóa địa chỉ: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            // Reload data to restore original state
            loadAndDisplayAddresses();
        }
    }

    private void updateLocalDatabase() {
        if (accountId != null && currentAddressList != null) {
            try {
                // Update local database with new address list
                Account account = AccountDatabase.getInstance(this).accountDAO().getAccountById(accountId);
                if (account != null) {
                    // Create a new copy of the address list for the account
                    List<Address> updatedList = new ArrayList<>(currentAddressList);
                    account.setAddressList(updatedList);
                    AccountDatabase.getInstance(this).accountDAO().updateAccount(account);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Lỗi cập nhật cơ sở dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}