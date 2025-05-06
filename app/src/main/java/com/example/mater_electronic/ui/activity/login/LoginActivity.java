package com.example.mater_electronic.ui.activity.login;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mater_electronic.R;

public class LoginActivity extends AppCompatActivity {
    private EditText email_input;
    private EditText password_input;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(v -> {
            String email = email_input.getText().toString();
            String password = password_input.getText().toString();
            // Add your login logic here
            Log.e("Login", "Email: " + email + ", Password: " + password);
        });
    }
}