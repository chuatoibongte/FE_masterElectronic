package com.example.mater_electronic.ui.activity.searchresult;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivitySearchResultBinding;
import com.example.mater_electronic.models.ProductItem;
import com.example.mater_electronic.models.product.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private SearchResultViewModel searchResultViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchResultBinding binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // 2 cột
        binding.rvSearchResult.setLayoutManager(gridLayoutManager);

        List<Product> productList = new ArrayList<>();

        SearchResultAdapter adapter = new SearchResultAdapter(productList);
        binding.rvSearchResult.setAdapter(adapter);

        searchResultViewModel = new ViewModelProvider(this).get(SearchResultViewModel.class);

        String keyword = getIntent().getStringExtra("keyword");
        if (keyword != null) {
            binding.etSearch.setText(keyword);
            searchResultViewModel.getSearchResults(keyword, "", "", "", "", 1, 10);
        }

        searchResultViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    binding.loadingOverlay.setVisibility(View.VISIBLE);
                } else {
                    binding.loadingOverlay.setVisibility(View.GONE);
                }
            }
        });

        searchResultViewModel.getSearchResults().observe(this, products -> {
            if (products != null) {
                if(products.isEmpty()){
                    // Hiển thị thông báo không tìm thấy sản phẩm
                    binding.tvNotFound.setText("Không tìm thấy sản phẩm");
                    return;
                }
                binding.tvNotFound.setText("");
                SearchResultAdapter newAdapter = new SearchResultAdapter(products);
                binding.rvSearchResult.setAdapter(newAdapter);
            }
        });

        searchResultViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });



        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    binding.rvSearchResult.setAdapter(new SearchResultAdapter(new ArrayList<>()));
                    // Toast.makeText(SearchResultActivity.this, binding.etSearch.getText().toString(), Toast.LENGTH_SHORT).show();
                    String keyword = binding.etSearch.getText().toString();
                    searchResultViewModel.getSearchResults(keyword, "", "", "", "", 1, 10);
                    binding.etSearch.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }
}