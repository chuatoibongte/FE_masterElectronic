package com.example.mater_electronic.ui.activity.profile.myaddress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.databinding.ActivityMyAddressBinding;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.Address;

import java.util.List;

public class MyAddress extends AppCompatActivity {
    public ActivityMyAddressBinding binding;
    private AddressAdapter addressAdapter;
    private String accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //back Button
        binding.backArrow.setOnClickListener(v -> finish());

        //Lấy _id từ sharedPreferences
        accountId = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("_id", null);

        setupRecyclerView();
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

    private void loadAndDisplayAddresses() {
        if (accountId == null) {
            Toast.makeText(this, "Không thể lấy thông tin tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }

        //Lấy dữ liệu từ account và hiển thị
        Account account = AccountDatabase.getInstance(this).accountDAO().getAccountById(accountId);

        if (account != null && account.getAddressList() != null && !account.getAddressList().isEmpty()) {
            displayAddresses(account.getAddressList());
        } else {
            Toast.makeText(this, "Không có địa chỉ nào", Toast.LENGTH_SHORT).show();
            // Clear the adapter if no addresses
            if (addressAdapter != null) {
                addressAdapter.updateAddressList(null);
            }
        }
    }

    private void setupRecyclerView() {
        binding.rvAddress.setLayoutManager(new LinearLayoutManager(this));
        addressAdapter = new AddressAdapter(null);
        binding.rvAddress.setAdapter(addressAdapter);
    }

    private void displayAddresses(List<Address> addressList) {
        if (addressAdapter != null) {
            addressAdapter.updateAddressList(addressList);
        }
    }
}