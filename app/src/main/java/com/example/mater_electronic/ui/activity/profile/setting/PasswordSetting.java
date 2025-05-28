package com.example.mater_electronic.ui.activity.profile.setting;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.R;
import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.databinding.ActivityPasswordSettingBinding;
import com.example.mater_electronic.databinding.ActivitySettingBinding;
import com.example.mater_electronic.utils.PasswordToggleUtil;
import com.example.mater_electronic.viewmodels.AccountViewModel;

public class PasswordSetting extends AppCompatActivity {
    private ActivityPasswordSettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPasswordSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Account model
        AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        //Get accessToken from preferenced
        String accessToken = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("accessToken", null);


        //back Button
        binding.backArrow.setOnClickListener(v -> finish());

        //Toggle mật khẩu cho 3 nút
        PasswordToggleUtil.setupPasswordToggle(binding.passwordInput, binding.eyeIcon);
        PasswordToggleUtil.setupPasswordToggle(binding.newPasswordInput, binding.eyeIcon2);
        PasswordToggleUtil.setupPasswordToggle(binding.checkNewPasswordInput, binding.eyeIcon3);

        //Quan sát sự thay đổi của LiveData
        // Observe update results
        accountViewModel.getUpdateSuccess().observe(this, success -> {
            if (success != null) {
                if (success) {
                    Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Observe error messages
        accountViewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        });

        //Xử lý sự kiện khi nhấn nút đổi mật khẩu
        binding.changePassBtn.setOnClickListener(v -> {
            //Lấy dữ liệu từ input
            String password = binding.passwordInput.getText().toString();
            String newpassword = binding.newPasswordInput.getText().toString();
            String checkNewPassword = binding.checkNewPasswordInput.getText().toString();

            //Kiểm tra newPassword
            if (!newpassword.equals(checkNewPassword)) {
                Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            //Call API đổi mật khẩu
            accountViewModel.changePass(accessToken, password, newpassword);
        });
    }

    // Giải phóng binding để tránh memory leak
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}