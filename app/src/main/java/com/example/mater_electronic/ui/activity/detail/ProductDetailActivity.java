package com.example.mater_electronic.ui.activity.detail;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivityProductDetailBinding;
import com.example.mater_electronic.models.product.ElectronicImg;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.utils.LoadImageByUrl;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {
    private ActivityProductDetailBinding binding;
    private Handler handler;
    private Runnable autoScrollRunnable;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnBack.setOnClickListener(v -> finish());
        String productId = getIntent().getStringExtra("product_id");
//        String productId = "6814329cc86355927f0c3bf3";
        if (productId == "") {
            Toast.makeText(this, "Không có mã sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // set viewmodel
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getProductDetail(productId);
        productViewModel.getReviews(productId);
        // set recyclerview
        RecyclerView recyclerViewReviews = binding.customerReviewRecyclerView;
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        CustomerReviewAdapter customerReviewAdapter = new CustomerReviewAdapter(this);

        recyclerViewReviews.setAdapter(customerReviewAdapter);
        productViewModel.getProductReviewLiveData().observe(this, reviews -> {
            Log.e("ProductViewModel", "Reviews  : " + new Gson().toJson(reviews));
            if(reviews.isEmpty()) {
                binding.tvNoReview.setVisibility(View.VISIBLE);
            }
            customerReviewAdapter.setData(reviews);
        });

        productViewModel.getProductLiveData().observe(this, product -> {
            Log.e("ProductViewModel", "Product: " + new Gson().toJson(product));
            if (product != null) {
                currentProduct = product; // Store current product for bottom sheet

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
                binding.tvProductPrice.setText(formatPrice(product.getPrice()) + " ₫");
                binding.tvProductDescription.setText(product.getDescription());
                binding.tvRating.setText(String.valueOf(product.getRating()));
                binding.productRatingBar.setRating((float) product.getRating());

                binding.btnAddToCart.setOnClickListener(v ->
                        Toast.makeText(this, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show());

                // Show bottom sheet when clicking Buy Now
                binding.btnBuyNow.setOnClickListener(v -> showQuantityBottomSheet(product));
            }
        });
    }

    private void showQuantityBottomSheet(Product product) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_quantity, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        // Initialize views
        ImageView ivProductImage = bottomSheetView.findViewById(R.id.ivProductImage);
        TextView tvProductName = bottomSheetView.findViewById(R.id.tvProductName);
        TextView tvProductPrice = bottomSheetView.findViewById(R.id.tvProductPrice);
        TextView tvQuantity = bottomSheetView.findViewById(R.id.tvQuantity);
        TextView tvTotalPrice = bottomSheetView.findViewById(R.id.tvTotalPrice);
        ImageButton btnDecrease = bottomSheetView.findViewById(R.id.btnDecrease);
        ImageButton btnIncrease = bottomSheetView.findViewById(R.id.btnIncrease);
        ImageButton btnClose = bottomSheetView.findViewById(R.id.btnClose);
        Button btnAddToCart = bottomSheetView.findViewById(R.id.btnAddToCart);
        Button btnBuyNow = bottomSheetView.findViewById(R.id.btnBuyNow);

        // Set product data
        tvProductName.setText(product.getName());
        tvProductPrice.setText(formatPrice(product.getPrice()) + " ₫");

        // Load product image
        if (!product.getElectronicImgs().isEmpty()) {
            LoadImageByUrl.loadImage(ivProductImage, product.getElectronicImgs().get(0).getUrl());
        }

        // Quantity management
        final int[] quantity = {1};
        final double productPrice = product.getPrice();

        // Update total price initially
        updateTotalPrice(tvTotalPrice, productPrice, quantity[0]);

        btnDecrease.setOnClickListener(v -> {
            if (quantity[0] > 1) {
                quantity[0]--;
                tvQuantity.setText(String.valueOf(quantity[0]));
                updateTotalPrice(tvTotalPrice, productPrice, quantity[0]);
            }
        });

        btnIncrease.setOnClickListener(v -> {
            quantity[0]++;
            tvQuantity.setText(String.valueOf(quantity[0]));
            updateTotalPrice(tvTotalPrice, productPrice, quantity[0]);
        });

        btnClose.setOnClickListener(v -> bottomSheetDialog.dismiss());

        btnAddToCart.setOnClickListener(v -> {
            Toast.makeText(this, "Đã thêm " + quantity[0] + " sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
        });

        btnBuyNow.setOnClickListener(v -> {
            Toast.makeText(this, "Mua " + quantity[0] + " sản phẩm - Tổng: " +
                    formatPrice(productPrice * quantity[0]) + " ₫", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
            // Here you can add logic to proceed to checkout/payment
        });

        bottomSheetDialog.show();
    }

    private void updateTotalPrice(TextView tvTotalPrice, double unitPrice, int quantity) {
        double totalPrice = unitPrice * quantity;
        tvTotalPrice.setText("Tổng: \n" + formatPrice(totalPrice) + " ₫");
    }

    private String formatPrice(double price) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(price);
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