package com.example.mater_electronic.ui.activity.profile.myaddress;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mater_electronic.R;
import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.databinding.ActivityMyAddressBinding;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.account.Address;

import java.util.List;

public class MyAddress extends AppCompatActivity {
    public ActivityMyAddressBinding binding;
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMyAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //back Button
        binding.backArrow.setOnClickListener(v -> finish());

        //Lấy _id từ sharedPreferences
        String _id = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("_id", null);

        //Lấy dữ liệu từ account và hiển thị
        Account account = AccountDatabase.getInstance(this).accountDAO().getAccountById(_id);

        setupRecyclerView();

        if (account != null && account.getAddressList() != null) {
            displayAddresses(account.getAddressList());
        } else {
            Toast.makeText(this, "Không có địa chỉ nào", Toast.LENGTH_SHORT).show();
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