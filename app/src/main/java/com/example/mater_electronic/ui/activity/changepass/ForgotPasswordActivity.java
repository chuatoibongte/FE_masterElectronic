package com.example.mater_electronic.ui.activity.changepass;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivityForgotPasswordBinding;
import com.example.mater_electronic.ui.activity.login.LoginActivity;
import com.example.mater_electronic.viewmodels.SendOTPViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Tạo SendOTPViewModel
        SendOTPViewModel sendOTPViewModel = new ViewModelProvider(this).get(SendOTPViewModel.class);

        sendOTPViewModel.getResultMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (message.contains("thành công")) {
                Intent intent = new Intent(this, OTP_confirm.class);
                intent.putExtra("email", binding.emailInput.getText().toString());
                startActivity(intent);
            }
        });
        // Khi call api đang loading
        sendOTPViewModel.getIsLoading().observe(this, isLoading -> {
            binding.forgotPasswordNext.setEnabled(!isLoading);
        });

        binding.forgotPasswordNext.setOnClickListener(v -> {
            String email = binding.emailInput.getText().toString();
            sendOTPViewModel.sendOTP(email);
        });
    }
}