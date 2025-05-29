package com.example.mater_electronic.ui.activity.profile.favorite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mater_electronic.databinding.ActivityMyFavoriteBinding;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.ui.activity.detail.ProductDetailActivity;

import java.util.ArrayList;

public class MyFavorite extends AppCompatActivity implements MyFavoriteAdapter.OnFavoriteClickListener {
    private ActivityMyFavoriteBinding binding;
    private MyFavoriteAdapter favoriteAdapter;
    private MyFavoriteViewModel viewModel;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();
        setupRecyclerView();
        setupClickListeners();
        getAccessToken();
        loadFavoriteProducts();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MyFavoriteViewModel.class);

        // Observe favorite products
        viewModel.getFavoriteProducts().observe(this, products -> {
            if (products != null && !products.isEmpty()) {
                favoriteAdapter.updateFavoriteProducts(products);
            } else {
                // Handle empty state
                favoriteAdapter.updateFavoriteProducts(new ArrayList<>());
                Toast.makeText(this, "Không có sản phẩm yêu thích", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe error messages
        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        favoriteAdapter = new MyFavoriteAdapter(this, new ArrayList<>());
        favoriteAdapter.setOnFavoriteClickListener(this);

        binding.rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFavorite.setAdapter(favoriteAdapter);
        binding.rvFavorite.setHasFixedSize(true);
    }

    private void setupClickListeners() {
        binding.backArrow.setOnClickListener(v -> finish());
    }

    private void getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("accessToken", "");

        if (accessToken.isEmpty()) {
            Toast.makeText(this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadFavoriteProducts() {
        if (!accessToken.isEmpty()) {
            viewModel.getFavorite(accessToken);
        }
    }

    @Override
    public void onFavoriteClick(Product product) {
        // Navigate to product detail activity
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("product_id", product.get_id());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh favorite products when returning to this activity
        loadFavoriteProducts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}