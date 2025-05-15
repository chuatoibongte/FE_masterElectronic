package com.example.mater_electronic.ui.navigation.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mater_electronic.databinding.ActivitySearchBinding;
import com.example.mater_electronic.ui.activity.search.SearchFocusActivity;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;

    private SearchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding.btnBack.setOnClickListener(v -> finish());
//        binding.etSearch.setOnFocusChangeListener( new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Intent intent = new Intent(SearchActivity.this, SearchFocusActivity.class);
//                startActivity(intent);
//            }
//        });
        binding.etSearch.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, SearchFocusActivity.class);
            startActivity(intent);
        });
    }
}