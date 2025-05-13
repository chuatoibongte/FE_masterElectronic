package com.example.mater_electronic.ui.activity.profile.edit;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivityEditAccountBinding;

public class EditAccountActivity extends AppCompatActivity {
    private ActivityEditAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Back button
        binding.backArrow.setOnClickListener(v -> finish());
    }
}