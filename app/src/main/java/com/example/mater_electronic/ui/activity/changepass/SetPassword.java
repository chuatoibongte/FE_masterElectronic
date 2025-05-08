package com.example.mater_electronic.ui.activity.changepass;

import com.example.mater_electronic.MainActivity;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.mater_electronic.network.ApiClient;
import com.example.mater_electronic.network.auth.AuthService;
import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivitySetPasswordBinding;
import com.example.mater_electronic.models.auth.ChangePasswordResponse;

public class SetPassword extends AppCompatActivity {

    private ActivitySetPasswordBinding binding;
    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy email và OTP từ Intent
        String email = getIntent().getStringExtra("email");
        String otp = getIntent().getStringExtra("otp");
        Toast.makeText(this, email + " - " + otp, Toast.LENGTH_SHORT).show();

        binding.backArrow.setOnClickListener(v -> finish());

        // Toggle hiển thị mật khẩu mới
        binding.newPasswordEyeIcon.setOnClickListener(v -> {
            isNewPasswordVisible = !isNewPasswordVisible;
            if (isNewPasswordVisible) {
                binding.newPassword.setTransformationMethod(SingleLineTransformationMethod.getInstance());
            } else {
                binding.newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            binding.newPassword.setSelection(binding.newPassword.getText().length());
        });

        // Toggle hiển thị xác nhận mật khẩu
        binding.confirmPasswordEyeIcon.setOnClickListener(v -> {
            isConfirmPasswordVisible = !isConfirmPasswordVisible;
            if (isConfirmPasswordVisible) {
                binding.confirmPassword.setTransformationMethod(SingleLineTransformationMethod.getInstance());
            } else {
                binding.confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            binding.confirmPassword.setSelection(binding.confirmPassword.getText().length());
        });

        // Xử lý nút thay đổi mật khẩu
        binding.changePass.setOnClickListener(v -> {
            String newPassword = binding.newPassword.getText().toString().trim();
            String confirmPassword = binding.confirmPassword.getText().toString().trim();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ cả hai trường mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPassword.length() < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            binding.changePass.setEnabled(false);

            // Gọi API
            changePassword(email, otp, newPassword);
        });
    }

    private void changePassword(String email, String otp, String newPassword) {
        AuthService api = ApiClient.getApiService();

        // Truyền trực tiếp từng trường thay vì dùng ChangePasswordRequest
        Call<ChangePasswordResponse> call = api.changePassword(email, otp, newPassword);

        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                binding.changePass.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    ChangePasswordResponse result = response.body();
                    if (Boolean.TRUE.equals(result.success)) {
                        Toast.makeText(SetPassword.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SetPassword.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SetPassword.this, result.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SetPassword.this, "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                binding.changePass.setEnabled(true);
                Toast.makeText(SetPassword.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
