package com.example.mater_electronic.ui.navigation.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.databinding.FragmentProfileBinding;
import com.example.mater_electronic.ui.activity.login.LoginActivity;
import com.example.mater_electronic.ui.activity.profile.edit.EditAccountActivity;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Sửa lại tên biến ở đây
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Khi người dùng ấn đăng xuất, xóa dữ liệu Shared Preferences
        binding.logoutLayout.setOnClickListener(v -> {
            SharedPreferences prefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        //Go to edit user
        binding.editBtn.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), EditAccountActivity.class);
            startActivity(intent);
        });

        //Go to edit user through ptions button
        binding.editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditAccountActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
