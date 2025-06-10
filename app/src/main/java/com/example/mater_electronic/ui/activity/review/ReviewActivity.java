package com.example.mater_electronic.ui.activity.review;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.models.review.CreateReviewResponse;
import com.example.mater_electronic.models.review.Review;
import com.example.mater_electronic.utils.LoadImageByUrl;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {
    private String reviewID = "";
    private ReviewViewModel reviewViewModel;

    private ImageView btnBack;
    private ImageView ivProductImage;
    private TextView tvProductName;
    private RatingBar ratingBar;
    private EditText etReviewContent;
    private Button btnPickImages, btnSubmitReview, btnDeleteReview;
    private RecyclerView rvSelectedImages;
    private FrameLayout loadingOverlay;
    private ProgressBar progressBar;

    private ArrayList<Uri> imageUris = new ArrayList<>();
    private ImagePickerAdapter imagePickerAdapter;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    ClipData clipData = result.getData().getClipData();
                    if (clipData != null) {
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            imageUris.add(clipData.getItemAt(i).getUri());
                        }
                    } else {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            imageUris.add(imageUri);
                        }
                    }
                    imagePickerAdapter.notifyDataSetChanged();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        btnBack = findViewById(R.id.backBtn);
        ivProductImage = findViewById(R.id.ivProductImage);
        tvProductName = findViewById(R.id.tvProductName);
        ratingBar = findViewById(R.id.ratingBar);
        etReviewContent = findViewById(R.id.etReviewContent);
        btnPickImages = findViewById(R.id.btnPickImages);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
        btnDeleteReview = findViewById(R.id.btnDeleteReview);
        rvSelectedImages = findViewById(R.id.rvSelectedImages);
        loadingOverlay = findViewById(R.id.loadingOverlay);
        progressBar = findViewById(R.id.progressBar);

        btnBack.setOnClickListener(v -> finish());
        // Nhận dữ liệu từ intent
        String electronicID = getIntent().getStringExtra("electronicID");
        String productName = getIntent().getStringExtra("productName");
        String productImageUrl = getIntent().getStringExtra("productImageUrl");

        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        reviewViewModel.getIsLoading().observe(this, isLoading -> {
            if(isLoading) {
                loadingOverlay.setVisibility(View.VISIBLE);
            } else {
                loadingOverlay.setVisibility(View.GONE);
            }
        });

        reviewViewModel.checkReviewExist(getCurrentUserAccessToken(), electronicID);

        reviewViewModel.getCheckIsExistedMessage().observe(this, message -> {
            if(message.equals("existed")) {
                btnSubmitReview.setText("Cập nhật đánh giá");
                btnDeleteReview.setVisibility(View.VISIBLE);
//                rvSelectedImages.setVisibility(View.GONE);
//                btnPickImages.setVisibility(View.GONE);
                Toast.makeText(this, "Bạn đã đánh giá sản phẩm này rồi", Toast.LENGTH_SHORT).show();
                btnSubmitReview.setOnClickListener(v -> {
                    float rating = ratingBar.getRating();
                    String reviewText = etReviewContent.getText().toString();
                    String accessToken = getCurrentUserAccessToken(); // Bạn cần thay bằng token thật (ví dụ: lấy từ SharedPreferences)
                    reviewViewModel.updateReview(
                            accessToken,
                            String.valueOf(rating),
                            reviewText, electronicID,
                            reviewID,
                            imageUris,
                            ReviewActivity.this
                    );
                });
                btnDeleteReview.setOnClickListener(v -> {
                    reviewViewModel.deleteReview(
                            getCurrentUserAccessToken(),
                            electronicID,
                            reviewID
                    );
                });
            }
            else {
                // Gửi đánh giá
                btnSubmitReview.setOnClickListener(v -> {
                    float rating = ratingBar.getRating();
                    String reviewText = etReviewContent.getText().toString();
                    String accessToken = getCurrentUserAccessToken(); // Bạn cần thay bằng token thật (ví dụ: lấy từ SharedPreferences)
                    reviewViewModel.createReview(
                            accessToken,
                            String.valueOf(rating),
                            reviewText,
                            electronicID,
                            imageUris,
                            ReviewActivity.this
                    );
                });
                Toast.makeText(this, "Bạn chưa đánh giá sản phẩm này", Toast.LENGTH_SHORT).show();
            }
        });

        reviewViewModel.getExistedReview().observe(this, existedReview -> {
            if(existedReview == null) {
                return;
            }
            Review review = existedReview;
            reviewID = review.get_id();
            ratingBar.setRating((float) review.getRating());
            etReviewContent.setText(review.getContent());
        });

        reviewViewModel.getIsReviewSuccess().observe(this, isReviewSuccess -> {
            if(isReviewSuccess) {
                Toast.makeText(ReviewActivity.this, "Gửi đánh giá thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Toast.makeText(ReviewActivity.this, "Gửi đánh giá thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        reviewViewModel.getIsUpdateReviewSuccess().observe(this, isUpdateReviewSuccess -> {
            if(isUpdateReviewSuccess) {
                Toast.makeText(ReviewActivity.this, "Cập nhật đánh giá thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Toast.makeText(ReviewActivity.this, "Cập nhật đánh giá thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        reviewViewModel.getIsDeleteReviewSuccess().observe(this, isDeleteReviewSuccess -> {
            if(isDeleteReviewSuccess) {
                Toast.makeText(ReviewActivity.this, "Xóa đánh giá thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Toast.makeText(ReviewActivity.this, "Xóa đánh giá thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        reviewViewModel.getErrorMessage().observe(this, errorMessage -> {
            Log.e("ReviewActivity", "Error message: " + errorMessage);
            Toast.makeText(ReviewActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        tvProductName.setText(productName);
        LoadImageByUrl.loadImage(ivProductImage, productImageUrl); // ảnh sản phẩm

        // RecyclerView ảnh đã chọn
        imagePickerAdapter = new ImagePickerAdapter(imageUris);
        rvSelectedImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvSelectedImages.setAdapter(imagePickerAdapter);

        // Button chọn ảnh
        btnPickImages.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("image/*");
            imagePickerLauncher.launch(Intent.createChooser(intent, "Chọn ảnh"));
        });
    }
    private String getCurrentUserId() {
        SharedPreferences prefs = this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("_id", "");
    }
    private String getCurrentUserAccessToken() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("accessToken", null);
    }
}
