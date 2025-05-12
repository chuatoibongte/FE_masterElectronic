package com.example.mater_electronic.ui.activity.detail;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.databinding.ActivityProductDetailBinding;
import com.example.mater_electronic.utils.LoadImageByUrl;
import com.google.gson.Gson;

public class ProductDetailActivity extends AppCompatActivity {
    private ActivityProductDetailBinding binding;
    private ProductViewModel productViewModel;

    private RecyclerView recyclerViewReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // String productId = getIntent().getStringExtra("product_id");
        String productId = "6814329cc86355927f0c3bf3";
        if (productId == "") {
            Toast.makeText(this, "Không có mã sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getProductDetail(productId);
        productViewModel.getReviews(productId);

        recyclerViewReviews = binding.customerReviewRecyclerView;
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        CustomerReviewAdapter customerReviewAdapter = new CustomerReviewAdapter(this);

        recyclerViewReviews.setAdapter(customerReviewAdapter);
        productViewModel.getProductReviewLiveData().observe(this, reviews -> {
            Log.e("ProductViewModel", "Reviews  : " + new Gson().toJson(reviews));
            customerReviewAdapter.setData(reviews);
        });

        productViewModel.getProductLiveData().observe(this, product -> {
            Log.e("ProductViewModel", "Product: " + new Gson().toJson(product));
            if (product != null) {
                binding.tvProductName.setText(product.getName());
                binding.tvProductPrice.setText(product.getPrice() + " ₫");
                binding.tvProductDescription.setText(product.getDescription());
                binding.tvRating.setText("⭐ " + product.getRating());
                // load ảnh sử dụng Glide
                LoadImageByUrl.loadImage(binding.ivProductImage, product.getElectronicImgs().get(0).getUrl());

                binding.btnAddToCart.setOnClickListener(v ->
                        Toast.makeText(this, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show());

                binding.btnBuyNow.setOnClickListener(v ->
                        Toast.makeText(this, "Mua ngay chưa hỗ trợ", Toast.LENGTH_SHORT).show());
            }
        });
    }
}