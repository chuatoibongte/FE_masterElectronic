package com.example.mater_electronic.ui.activity.profile.edit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mater_electronic.R;
import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.databinding.ActivityEditAccountBinding;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.utils.LoadImageByUrl;

import java.util.Calendar;
import java.util.Locale;


import java.text.SimpleDateFormat;

public class EditAccountActivity extends AppCompatActivity {
    private ActivityEditAccountBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Back button
        binding.backArrow.setOnClickListener(v -> finish());

        //Lấy _id từ sharedPreferences
        String _id = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("_id", null);

        //Lấy dữ liệu từ account và hiển thị
        Account account = AccountDatabase.getInstance(this).accountDAO().getAccountById(_id);

        if (account != null) {
            binding.usernameEdt.setText(account.getUsername());
            binding.fullnameEdt.setText(account.getName());
            binding.emailEdt.setText(account.getEmail());
            binding.phoneInput.setText(account.getPhone());
            //Chuyển đổi ngày sinh thành định dạng dd/MM/yyyy
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            if (account.getBirthday() != null) {
                String formattedDate = sdf.format(account.getBirthday());
                binding.birthEdit.setText(formattedDate);
            }

            binding.gender.setText(account.getGender());

            if (account.getAvatar() != null && account.getAvatar().getUrl() != null) {
                LoadImageByUrl.loadImage(binding.profileImg, account.getAvatar().getUrl());
            }
        }

        // Đăng ký launcher Photo Picker
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                selectedImageUri = uri;
                binding.profileImg.setImageURI(uri); // hiển thị ảnh mới
                Log.d("PhotoPicker", "Selected image URI: " + uri.toString());
            } else {
                Toast.makeText(this, "Không có ảnh nào được chọn", Toast.LENGTH_SHORT).show();
            }
        });

// Sự kiện click vào cameraView để mở picker
        binding.cameraView.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

//        Chọn ngày sinh với DatePicker
        binding.calendarImg.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String dob = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                binding.birthEdit.setText(dob);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

//        Cập nhật account
        binding.editBtn.setOnClickListener(v -> {
            // Lấy dữ liệu từ EditText
            String newUsername = binding.usernameEdt.getText().toString().trim();
            String newName = binding.fullnameEdt.getText().toString().trim();
            String newEmail = binding.emailEdt.getText().toString().trim();
            String newPhone = binding.phoneInput.getText().toString().trim();
            String newBirth = binding.birthEdit.getText().toString().trim();
            String newGender = binding.gender.getText().toString().trim();

            // Cập nhật dữ liệu vào Account object
            account.setUsername(newUsername);
            account.setName(newName);
            account.setEmail(newEmail);
            account.setPhone(newPhone);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                account.setBirthday(sdf.parse(newBirth));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                return; // Không cập nhật nếu lỗi ngày sinh
            }

            account.setGender(newGender);

            // Cập nhật vào main database


            // Cập nhật vào local database
            AccountDatabase.getInstance(this).accountDAO().updateAccount(account);

            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

            finish();
        });


    }

}