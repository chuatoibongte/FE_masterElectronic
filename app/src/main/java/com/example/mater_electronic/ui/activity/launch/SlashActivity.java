package com.example.mater_electronic.ui.activity.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mater_electronic.R;

public class SlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slash);

        //Chờ 1,5 giây rồi sang màn hình mới
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SlashActivity.this, SecondSlashActivity.class);
            startActivity(intent);
            finish();
        }, 1500);
    }
}