package com.example.mater_electronic.ui.activity.login;

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

import com.example.mater_electronic.MainActivity;
import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivityLoginBinding;
import com.example.mater_electronic.ui.activity.changepass.ForgotPasswordActivity;
import com.example.mater_electronic.ui.activity.register.Register;
import com.example.mater_electronic.ui.navigation.home.HomeFragment;
import com.example.mater_electronic.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Tạo LoginModel
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        //Observe quan sát thay đổi để thực hiện Chuyển trang Home nếu đăng nhập thành công
        loginViewModel.getResultMessage().observe(this, message-> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if(message.contains("thành công")){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Khi call api đang loading (nên dùng process bar)
        loginViewModel.getIsLoading().observe(this, isLoading -> {
            binding.loginButton.setEnabled(!isLoading);
        });

        //Xử lý nút Đăng nhập
        binding.loginButton.setOnClickListener(v -> {
            String email = binding.emailInput.getText().toString();
            String password = binding.passwordInput.getText().toString();
            //Kiểm tra rỗng
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            //Call api đăng nhập
            loginViewModel.loginAccount(email,password);
        });

        binding.loginNowTxtClickable.setOnClickListener(v -> {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });
        binding.forgotPassBtn.setOnClickListener(v-> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
}