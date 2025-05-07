package com.example.mater_electronic.ui.activity.changepass;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivitySetPasswordBinding;

public class SetPassword extends AppCompatActivity {

    private ActivitySetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String email = getIntent().getStringExtra("email");
        String otp = getIntent().getStringExtra("otp");
        Toast.makeText(this, email + " - " + otp, Toast.LENGTH_SHORT).show();
    }
}