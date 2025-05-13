package com.example.mater_electronic.ui.activity.detail;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mater_electronic.databinding.ActivityProductDetailBinding;
import com.example.mater_electronic.models.product.ElectronicImg;
import com.example.mater_electronic.utils.LoadImageByUrl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private ActivityProductDetailBinding binding;
    private ProductViewModel productViewModel;
    private Handler handler;
    private Runnable autoScrollRunnable;

    private RecyclerView recyclerViewReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(v -> finish());
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
                List<ProductImageItem> images = new ArrayList<>();
                for (ElectronicImg electronicImg : product.getElectronicImgs()) {
                    images.add(new ProductImageItem(electronicImg.getUrl()));
                }
                ProductImageAdapter productImageAdapter = new ProductImageAdapter(images);
                ViewPager2 vpProductImage = binding.vpProductImage;
                vpProductImage.setAdapter(productImageAdapter);
                if(!images.isEmpty()) {
                    int middlePosition = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % images.size());
                    vpProductImage.setCurrentItem(middlePosition, false);
                }
                // Thiết lập tự động chuyển ảnh với kiểm tra tương tác của người dùng
                handler = new Handler();
                autoScrollRunnable = new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = vpProductImage.getCurrentItem();
                        int nextItem = (currentItem == images.size() - 1) ? 0 : currentItem + 1;
                        vpProductImage.setCurrentItem(nextItem, true);
                        handler.postDelayed(this, 6000); // 6 giây
                    }
                };
                // Lắng nghe sự kiện thay đổi trang để tạm dừng auto scroll nếu người dùng tương tác
                vpProductImage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        handler.removeCallbacks(autoScrollRunnable);
                        handler.postDelayed(autoScrollRunnable, 6000); // Đặt lại auto scroll sau khi người dùng ngừng tương tác
                    }
                });

                startAutoScroll();

                binding.tvProductName.setText(product.getName());
                binding.tvProductPrice.setText(product.getPrice() + " ₫");
                binding.tvProductDescription.setText(product.getDescription());
                binding.tvRating.setText(String.valueOf(product.getRating()));
                binding.productRatingBar.setRating((float) product.getRating());
                // load ảnh sử dụng Glide
                // LoadImageByUrl.loadImage(binding.ivProductImage, product.getElectronicImgs().get(0).getUrl());

                binding.btnAddToCart.setOnClickListener(v ->
                        Toast.makeText(this, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show());

                binding.btnBuyNow.setOnClickListener(v ->
                        Toast.makeText(this, "Mua ngay chưa hỗ trợ", Toast.LENGTH_SHORT).show());
            }
        });
    }
    private void startAutoScroll() {
        if (handler != null && autoScrollRunnable != null) {
            handler.postDelayed(autoScrollRunnable, 6000); // Bắt đầu sau 6 giây
        }
    }
    private void stopAutoScroll() {
        if (handler != null && autoScrollRunnable != null) {
            handler.removeCallbacks(autoScrollRunnable);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll();
    }
    @Override
    public void onResume() {
        super.onResume();
        startAutoScroll(); // Tiếp tục khi Fragment hiển thị lại
    }
}