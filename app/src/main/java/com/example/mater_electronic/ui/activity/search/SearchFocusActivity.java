package com.example.mater_electronic.ui.activity.search;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivitySearchFocusBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFocusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchFocusBinding binding = ActivitySearchFocusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());
        // focus thanh search khi mở màn hình này
        binding.etSearch.requestFocus();


        RecyclerView searchHintRecyclerView = binding.rvSearchHint;
        searchHintRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        List<String> searchHints = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            searchHints.add("Search Hint " + i);
        }
        SearchFocusAdapter searchFocusAdapter = new SearchFocusAdapter(searchHints);
        searchHintRecyclerView.setAdapter(searchFocusAdapter);
    }
}