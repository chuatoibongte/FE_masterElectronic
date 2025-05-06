package com.example.mater_electronic.ui.navigation.favourite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.databinding.FragmentFavouriteBinding;

import com.example.mater_electronic.databinding.FragmentFavouriteBinding;

public class FavouriteFragment extends Fragment {

    private FragmentFavouriteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavouriteViewModel favouriteViewModel =
                new ViewModelProvider(this).get(FavouriteViewModel.class);

        // Inflate binding layout
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Kiểm tra xem 'binding.text_favourite' có đúng không
        TextView textView = binding.textFavourite;  // Đảm bảo tên ID trong XML là 'textFavourite'

        // Sử dụng ViewModel để cập nhật nội dung
        favouriteViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
