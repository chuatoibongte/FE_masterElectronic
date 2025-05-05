package com.example.mater_electronic.ui.navigation.my_cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.databinding.FragmentMycartBinding;

public class MyCartFragment extends Fragment {

    private FragmentMycartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyCartViewModel notificationsViewModel =
                new ViewModelProvider(this).get(MyCartViewModel.class);

        binding = FragmentMycartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMycart;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}