package com.example.mater_electronic.ui.activity.changepass;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mater_electronic.databinding.ActivityOtpConfirmBinding;

public class OTP_confirm extends AppCompatActivity {

    private ActivityOtpConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the binding layout
        binding = ActivityOtpConfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup OTP input boxes
        setupOtpInput();

        // Handle back button click
        binding.otpBackArrowImageView.setOnClickListener(v -> {
            finish(); // Close the activity
        });

        // Handle resend button click
        binding.buttonOtpGuilaima.setOnClickListener(v -> {
            Toast.makeText(this, "Mã OTP đã được gửi lại!", Toast.LENGTH_SHORT).show();
            // Add logic to resend OTP here (e.g., API call)
        });

        // Handle confirm button click
        binding.buttonOtpXacnhan.setOnClickListener(v -> {
            String otp = getOtpInput();
            if (otp.length() == 5) {
                // Add logic to verify OTP here (e.g., API call)
                Toast.makeText(this, "Xác nhận OTP: " + otp, Toast.LENGTH_SHORT).show();
                // Navigate to next activity or finish on success
                // e.g., startActivity(new Intent(this, NextActivity.class));
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ 5 chữ số OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupOtpInput() {
        EditText[] otpBoxes = {
                binding.otptest1,
                binding.otptest2,
                binding.otptest3,
                binding.otptest4,
                binding.otptest5
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
                binding.otptest5.getText().toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}