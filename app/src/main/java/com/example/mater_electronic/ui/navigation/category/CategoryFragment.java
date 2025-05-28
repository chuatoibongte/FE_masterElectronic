package com.example.mater_electronic.ui.navigation.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.databinding.FragmentCategoryBinding;
import com.example.mater_electronic.ui.activity.search.SearchFocusActivity;
import com.example.mater_electronic.ui.activity.searchresult.SearchResultActivity;
import com.example.mater_electronic.ui.navigation.search.SearchActivity;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CategoryViewModel homeViewModel =
                new ViewModelProvider(this).get(CategoryViewModel.class);

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.etSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchFocusActivity.class);
            startActivity(intent);
        });
        binding.txtLaptop.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchResultActivity.class);
            intent.putExtra("keyword", "Laptop");
            startActivity(intent);
        });
        binding.txtLinhkien.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchResultActivity.class);
            intent.putExtra("keyword", "Linh kiện");
            startActivity(intent);
        });
        binding.txtDienthoai.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchResultActivity.class);
            intent.putExtra("keyword", "Điện thoại");
            startActivity(intent);
        });
        binding.txtPC.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchResultActivity.class);
            intent.putExtra("keyword", "PC");
            startActivity(intent);
        });
        binding.txtPhukien.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchResultActivity.class);
            intent.putExtra("keyword", "Phụ kiện");
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
