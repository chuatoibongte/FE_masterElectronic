package com.example.mater_electronic.ui.navigation.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.databinding.FragmentProfileBinding;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.ui.activity.login.LoginActivity;
import com.example.mater_electronic.ui.activity.profile.edit.EditAccountActivity;
import com.example.mater_electronic.utils.LoadImageByUrl;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate layout using ViewBinding
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Lấy accessToken từ SharedPreferences để sử dụng khi cần gọi API hoặc xác thực
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("accessToken", "");
        Log.e("ProfileFragment", "Token: " + accessToken);

        // Truy vấn thông tin tài khoản đã lưu trong Room Database
        Account account = AccountDatabase.getInstance(requireActivity()).accountDAO().getAccount();

        // Nếu tài khoản tồn tại, hiển thị lên giao diện
        if (account != null) {
            displayAccountInfo(account);
        }

        // Thiết lập các sự kiện click (đăng xuất, chỉnh sửa tài khoản)
        setupListeners(prefs);

        return root;
    }

    // Hiển thị thông tin tài khoản lên UI
    private void displayAccountInfo(Account account) {
        // Hiển thị tên tài khoản
        binding.accountName.setText(account.getUsername());

        // Hiển thị ID rút gọn (tối đa 10 ký tự)
        String shortId = account.get_id().length() >= 10
                ? account.get_id().substring(0, 10)
                : account.get_id();
        binding.accountId.setText("ID: " + shortId);

        // Tải ảnh đại diện nếu có, nếu không thì đặt ảnh mặc định
        if (account.getAvatar() != null && account.getAvatar().getUrl() != null) {
            LoadImageByUrl.loadImage(binding.profileImg, account.getAvatar().getUrl());
        } else {
            binding.profileImg.setImageResource(com.example.mater_electronic.R.drawable.img_placeholder);
        }
    }

    // Thiết lập các sự kiện click: đăng xuất và chỉnh sửa hồ sơ
    private void setupListeners(SharedPreferences prefs) {
        // Sự kiện đăng xuất: xóa token, chuyển về màn hình đăng nhập
        binding.logoutLayout.setOnClickListener(v -> {
            prefs.edit().clear().apply(); // Xóa dữ liệu SharedPreferences

            // Chuyển sang màn hình đăng nhập
            startActivity(new Intent(getActivity(), LoginActivity.class));
            requireActivity().finish(); // Đóng màn hình hiện tại
        });

        // Sự kiện chỉnh sửa tài khoản (cả nút chính và nút trong menu)
        View.OnClickListener editClickListener = v -> {
            Intent intent = new Intent(getActivity(), EditAccountActivity.class);
            startActivity(intent);
        };

        binding.editBtn.setOnClickListener(editClickListener);
        binding.editProfile.setOnClickListener(editClickListener);
    }

    // Giải phóng binding để tránh memory leak
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
