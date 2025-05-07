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
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivityRegisterBinding;
import com.example.mater_electronic.ui.activity.login.LoginActivity;
import com.example.mater_electronic.viewmodels.RegisterViewModel;


public class Register extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //View Binding
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Tạo RegisterModel
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        //Observe quan sát thay đổi để thực hiện Chuyển sang trang đăng nhập nếu đăng ký thành công
        registerViewModel.getResultMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (message.contains("thành công")) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Khi call api đang loading
        registerViewModel.getIsLoading().observe(this, isLoading -> {
            binding.signupButton.setEnabled(!isLoading);
        });

        //Xử lý nhấn nút đăng ký
        binding.signupButton.setOnClickListener(v -> {
            String username = binding.usernameInput.getText().toString();
            String email = binding.emailInput.getText().toString();
            String phone = binding.phoneInput.getText().toString();
            String password = binding.passwordInput.getText().toString();
            String confirmpass = binding.confirmPasswordInput.getText().toString();

            // Kiểm tra rỗng
            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmpass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra mật khẩu trùng khớp
            if (!password.equals(confirmpass)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            //Call api đăng ký
            registerViewModel.registerAccount(username,email,phone,password);
        });
        binding.loginNowTxtClickable.setOnClickListener(v -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });
    }
}