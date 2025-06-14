package com.example.mater_electronic.ui.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mater_electronic.MainActivity;
import com.example.mater_electronic.R;
import com.example.mater_electronic.database.cart.CartDAO;
import com.example.mater_electronic.database.cart.CartDatabase;
import com.example.mater_electronic.databinding.ActivityProductDetailBinding;
import com.example.mater_electronic.models.cart.CartItem;
import com.example.mater_electronic.models.product.ElectronicImg;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.ui.activity.profile.favorite.MyFavoriteViewModel;
import com.example.mater_electronic.ui.activity.login.LoginActivity;
import com.example.mater_electronic.ui.navigation.my_cart.MyCartFragment;
import com.example.mater_electronic.utils.LoadImageByUrl;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class ProductDetailActivity extends AppCompatActivity {
    private ActivityProductDetailBinding binding;
    private Handler handler;
    private MyFavoriteViewModel myFavoriteViewModel;
    private Runnable autoScrollRunnable;
    private Product currentProduct;
    private Context context;
    private String accessToken;
    private boolean isFavorite = false;
    private int currentFollowersCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnMyCart.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("target_nav", R.id.navigation_mycart); // ID trong menu bottom navigation
            startActivity(intent);
            finish();
        });
        binding.btnChatbot.setOnClickListener(v -> {
            Toast.makeText(this, "Chatbot", Toast.LENGTH_SHORT).show();
        });

        //Lấy accessToken nếu có
        getAccessToken();

        //Lấy mã sản phẩm
        String productId = getIntent().getStringExtra("product_id");
//        String productId = "6814329cc86355927f0c3bf3";
        if (productId == "") {
            Toast.makeText(this, "Không có mã sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // set viewmodel
        myFavoriteViewModel = new ViewModelProvider(this).get(MyFavoriteViewModel.class);
        ProductViewModel productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getProductDetail(productId);
        productViewModel.getReviews(productId);

        //Get check favorite api
        myFavoriteViewModel.checkFavourite(accessToken, productId);
        // set recyclerview
        RecyclerView recyclerViewReviews = binding.customerReviewRecyclerView;
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        CustomerReviewAdapter customerReviewAdapter = new CustomerReviewAdapter(this);

        // Setup favorite observers
        setupFavoriteObservers();

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
                binding.productRatingBar.setRating(product.getRating());
                binding.tvFollowers.setText(String.valueOf(product.getFollowers()));

                //get current followers count
                currentFollowersCount = product.getFollowers();

                binding.btnAddToCart.setOnClickListener(v -> {
                    if(accessToken == null) {
                        showLoginRequiredDialog();
                    }
                    else showQuantityBottomSheet(product);
                });
                
                // Setup favorite button click listener
                setupFavoriteButton(product);

                // Show bottom sheet when clicking Buy Now
                binding.btnBuyNow.setOnClickListener(v -> {
                    if(accessToken == null) {
                        showLoginRequiredDialog();
                    }
                    else showQuantityBottomSheet(product);
                });
            }
        });
    }

    private void getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("accessToken", null);

//        if (accessToken == null || accessToken.isEmpty()) {
//            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
//        }
    }

    private void setupFavoriteObservers() {
        // Observe result message from adding to favorite
        myFavoriteViewModel.getResultMessage().observe(this, message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                // Toggle favorite state after successful addition and deletion
                isFavorite = !isFavorite;
                updateFavoriteIcon();
            }
        });

        //Observe isFavorite from checking favorite
        myFavoriteViewModel.getIsFavorite().observe(this, isFavorite -> {
            this.isFavorite = isFavorite;
            updateFavoriteIcon();
        });

        // Observe error messages
        myFavoriteViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupFavoriteButton(Product product) {
        binding.ivFavorite.setOnClickListener(v -> {
            // Check if accessToken is null or empty
            if (accessToken == null || accessToken.isEmpty()) {
                Toast.makeText(this, "Vui lòng đăng nhập để thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isFavorite) {
                myFavoriteViewModel.addFavourite(accessToken, product.get_id());
                currentFollowersCount++;
            } else {
                myFavoriteViewModel.deleteFavorite(accessToken, product.get_id());
                currentFollowersCount--;
            }

            binding.tvFollowers.setText(String.valueOf(currentFollowersCount));
        });
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            binding.ivFavorite.setImageResource(R.drawable.ic_heart_filled);
            binding.ivFavorite.setColorFilter(getResources().getColor(R.color.lightred));
        } else {
            binding.ivFavorite.setImageResource(R.drawable.ic_heart);
            binding.ivFavorite.clearColorFilter();
        }
    }



    // show bottom sheet để chọn số lượng
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
            // Executors.newSingleThreadExecutor().execute(() -> {
                CartDatabase db = CartDatabase.getInstance(context);
                CartDAO cartDAO = db.cartDAO();

                String userId = getCurrentUserId(); // take userId from sharedpreferences
                String productId = product.get_id(); // take _id of product
                CartItem existingItem = cartDAO.getCartItemByProductId(productId, userId);

                if (existingItem != null) {
                    // Cập nhật số lượng nếu đã có trong giỏ hàng
                    existingItem.setQuantity(existingItem.getQuantity() + quantity[0]);
                    cartDAO.updateCartItem(existingItem);
                } else {
                    String imageUrl = product.getElectronicImgs() != null && !product.getElectronicImgs().isEmpty()
                            ? product.getElectronicImgs().get(0).getUrl()
                            : "";

                    CartItem newItem = new CartItem(
                            productId,
                            product.getName(),
                            imageUrl,
                            product.getPrice(),
                            quantity[0],
                            product.getMainCategory(), // ← hoặc product.getSlugCate(), tùy theo bạn muốn hiển thị gì
                            userId
                    );
                    cartDAO.insertCartItem(newItem);
                }

                //new Handler(Looper.getMainLooper()).post(() -> {
                    new AlertDialog.Builder(context)
                            .setTitle("Thành công")
                            .setMessage("Đã thêm sản phẩm vào giỏ hàng.")
                            .setPositiveButton("Xem giỏ hàng", (dialog, which) -> {
                                // You can add any additional actions here if needed
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("target_nav", R.id.navigation_mycart); // ID của fragment trong nav_graph
                                startActivity(intent);

                            })
                            .setNegativeButton("Đóng", null)
                            .show();
                    bottomSheetDialog.dismiss();
                //});
            //});
        });


        btnBuyNow.setOnClickListener(v -> {
            btnAddToCart.performClick();
            bottomSheetDialog.dismiss();
            binding.btnMyCart.performClick();
            // Here you can add logic to proceed to checkout/payment
        });

        bottomSheetDialog.show();
    }
    // alert dialog yêu cầu đăng nhập để thực hiện chức năng nào đó
    private void showLoginRequiredDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_login_required, null);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnLogin = dialogView.findViewById(R.id.btn_login);

        btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        btnLogin.setOnClickListener(v -> {
            alertDialog.dismiss();
            // Chuyển sang màn hình đăng nhập
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        alertDialog.show();
    }
    // update tổng tiền
    private void updateTotalPrice(TextView tvTotalPrice, double unitPrice, int quantity) {
        double totalPrice = unitPrice * quantity;
        tvTotalPrice.setText("Tổng: \n" + formatPrice(totalPrice) + " ₫");
    }
    // format giá về dạng xxx.xxx.xxx
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
    private String getCurrentUserId() {
        SharedPreferences prefs = getApplication().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("_id", "");
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}