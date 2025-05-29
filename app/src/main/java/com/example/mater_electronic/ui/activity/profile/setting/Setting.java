package com.example.mater_electronic.ui.activity.profile.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivitySettingBinding;
import com.example.mater_electronic.ui.activity.profile.notification.Notification;

public class Setting extends AppCompatActivity {
    private ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //View binding
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Back Arrow Btn
        binding.backBtn.setOnClickListener(v -> finish());

        //To Setting Pass Activity
        binding.settingPassLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, PasswordSetting.class));
        });

        //To Notification Activity
        binding.settingNotificationLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, Notification.class));
        });

    }

    // Giải phóng binding để tránh memory leak
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}