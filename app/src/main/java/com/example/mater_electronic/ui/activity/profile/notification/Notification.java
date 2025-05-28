package com.example.mater_electronic.ui.activity.profile.notification;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivityNotificationBinding;

public class Notification extends AppCompatActivity {
    public ActivityNotificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());;

        //Back Arrow Btn
        binding.backBtn.setOnClickListener(v -> finish());

    }
}