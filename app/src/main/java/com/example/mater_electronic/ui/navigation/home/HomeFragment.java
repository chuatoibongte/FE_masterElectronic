package com.example.mater_electronic.ui.navigation.home;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.databinding.FragmentHomeBinding;
import com.example.mater_electronic.ui.activity.register.Register;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel dashboardViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Xử lý click nút Search
        binding.buttonSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), search.class);
            startActivity(intent);
        });

        // Xử lý click nút Register
        binding.registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Register.class);
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