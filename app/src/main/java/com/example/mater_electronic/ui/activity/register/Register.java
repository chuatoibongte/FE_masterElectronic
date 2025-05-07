package com.example.mater_electronic.ui.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivityRegisterBinding;
import com.example.mater_electronic.ui.activity.login.LoginActivity;

public class Register extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //View Binding
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupButton.setOnClickListener(v -> {
            String username = binding.usernameInput.getText().toString();
            String email = binding.emailInput.getText().toString();
            String phone = binding.phoneInput.getText().toString();
            String password = binding.passwordInput.getText().toString();

            // Hiển thị thông tin đăng ký bằng Toast
            String message = "Username: " + username + "\nEmail: " + email + "\nPhone: " + phone + "\nPassword: " + password;
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        });
        binding.loginNowTxtClickable.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });
    }
}