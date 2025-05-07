package com.example.mater_electronic.ui.activity.launch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mater_electronic.MainActivity;
import com.example.mater_electronic.R;
import com.example.mater_electronic.ui.activity.login.LoginActivity;

public class SecondSlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second_slash);

        //Chờ 1,5 giây rồi sang màn hình mới
        new Handler().postDelayed(() -> {

            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String accessToken = prefs.getString("accessToken", null);
            if (accessToken != null) {
                Intent intent = new Intent(SecondSlashActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(SecondSlashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            finish();
        }, 1500);
    }
}