package com.example.mater_electronic.ui.activity.changepass;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.databinding.ActivityOtpConfirmBinding;
import com.example.mater_electronic.viewmodels.OTPConfirmViewModel;
import com.example.mater_electronic.viewmodels.SendOTPViewModel;

public class OTP_confirm extends AppCompatActivity {

    private ActivityOtpConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the binding layout
        binding = ActivityOtpConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize OTPConfirmViewModel
        OTPConfirmViewModel otpConfirmViewModel = new ViewModelProvider(this).get(OTPConfirmViewModel.class);
        // Initialize SendOTPViewModel for re-send OTP
        SendOTPViewModel sendOTPViewModel = new ViewModelProvider(this).get(SendOTPViewModel.class);
        // get email sent from ForgotPasswordActivity
        String email = getIntent().getStringExtra("email");
        // Mask the email
        String maskedEmail = maskEmail(email);
        binding.yourEmail.setText(maskedEmail);

        // Setup OTP input boxes
        setupOtpInput();

        // Handle back button click
        binding.otpBackArrowImageView.setOnClickListener(v -> {
            finish(); // Close the activity
        });

        // Handle resend button click
        binding.buttonOtpGuilaima.setOnClickListener(v -> {
            // Send OTP again
            sendOTPViewModel.sendOTP(email);
            Toast.makeText(this, "Mã OTP đã được gửi lại!", Toast.LENGTH_SHORT).show();
            // Add logic to resend OTP here (e.g., API call)
        });

        // Handle OTP verification result
        otpConfirmViewModel.getResultMessage().observe(this, message -> {
            if(message.contains("thành công")) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                String otp = getOtpInput();
                Intent intent = new Intent(this, SetPassword.class);
                intent.putExtra("email", email);
                intent.putExtra("otp", otp);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Mã OTP không chính xác", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle confirm button click
        binding.buttonOtpXacnhan.setOnClickListener(v -> {
            String otp = getOtpInput();
            if (otp.length() == 6) {
                // Add logic to verify OTP here (e.g., API call)
                otpConfirmViewModel.verifyOTP(email, otp);
                Toast.makeText(this, "Xác nhận OTP: " + otp, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ 6 chữ số OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupOtpInput() {
        EditText[] otpBoxes = {
                binding.otptest1,
                binding.otptest2,
                binding.otptest3,
                binding.otptest4,
                binding.otptest5,
                binding.otptest6
        };

        for (int i = 0; i < otpBoxes.length; i++) {
            final int index = i;
            otpBoxes[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 1 && index < otpBoxes.length - 1) {
                        otpBoxes[index + 1].requestFocus();
                    } else if (s.length() == 0 && index > 0) {
                        otpBoxes[index - 1].requestFocus();
                    }
                }
            });
        }
    }

    private String getOtpInput() {
        return binding.otptest1.getText().toString() +
                binding.otptest2.getText().toString() +
                binding.otptest3.getText().toString() +
                binding.otptest4.getText().toString() +
                binding.otptest5.getText().toString() +
                binding.otptest6.getText().toString();
    }

    private String maskEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            return email; // Return original email if it's invalid
        }

        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        int visibleChars = Math.min(2, username.length() / 2);
        String maskedUsername = username.substring(0, visibleChars) + "*".repeat(username.length() - visibleChars) ;

        return maskedUsername + "@" + domain;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}