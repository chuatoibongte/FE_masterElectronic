package com.example.mater_electronic.ui.activity.searchresult;

import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.MainActivity;
import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivitySearchResultBinding;
import com.example.mater_electronic.localdata.DataLocalManager;
import com.example.mater_electronic.models.ProductItem;
import com.example.mater_electronic.models.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchResultActivity extends AppCompatActivity {
    private String sortBy = "";
    private String sortOrder = "";
    private SearchResultViewModel searchResultViewModel;
    private SearchResultAdapter adapter;
    private List<Product> productList;
    private String currentKeyword = "";
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchResultBinding binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // 2 cột
        binding.rvSearchResult.setLayoutManager(gridLayoutManager);

        binding.btnMyCart.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("target_nav", R.id.navigation_mycart); // ID trong menu bottom navigation
            startActivity(intent);
            finish();
        });

        productList = new ArrayList<>();
        adapter = new SearchResultAdapter(productList);
        binding.rvSearchResult.setAdapter(adapter);

        binding.bestSeller.setOnClickListener(v -> {
            if(Objects.equals(sortBy, "quantitySold")) {
                sortBy = "";
                binding.bestSeller.setBackgroundResource(0);
                binding.relatedTo.setBackgroundResource(R.drawable.button_background2);
                searchData(binding, currentKeyword, true);
                return;
            }
            sortBy = "quantitySold";
            binding.bestSeller.setBackgroundResource(R.drawable.button_background2);
            binding.relatedTo.setBackgroundResource(0);
            binding.newest.setBackgroundResource(0);
            binding.price.setBackgroundResource(0);
            searchData(binding, currentKeyword, true);
        });
        binding.newest.setOnClickListener(v -> {
            if(Objects.equals(sortBy, "publishDate")) {
                sortBy = "";
                binding.newest.setBackgroundResource(0);
                binding.relatedTo.setBackgroundResource(R.drawable.button_background2);
                searchData(binding, currentKeyword, true);
                return;
            }
            sortBy = "publishDate";
            binding.bestSeller.setBackgroundResource(0);
            binding.relatedTo.setBackgroundResource(0);
            binding.newest.setBackgroundResource(R.drawable.button_background2);
            binding.price.setBackgroundResource(0);
            searchData(binding, currentKeyword, true);
        });
        binding.relatedTo.setOnClickListener(v -> {
            sortBy = "";
            binding.bestSeller.setBackgroundResource(0);
            binding.relatedTo.setBackgroundResource(R.drawable.button_background2);
            binding.newest.setBackgroundResource(0);
            binding.price.setBackgroundResource(0);
            searchData(binding, currentKeyword, true);
        });
        binding.price.setOnClickListener(v -> {
            if(Objects.equals(sortBy, "price")) {
                sortBy = "";
                binding.price.setBackgroundResource(0);
                binding.relatedTo.setBackgroundResource(R.drawable.button_background2);
                searchData(binding, currentKeyword, true);
                return;
            }
            sortBy = "price";
            binding.bestSeller.setBackgroundResource(0);
            binding.relatedTo.setBackgroundResource(0);
            binding.newest.setBackgroundResource(0);
            binding.price.setBackgroundResource(R.drawable.button_background2);
            searchData(binding, currentKeyword, true);
        });
        // sắp xếp tăng / giảm dần
        binding.tvOrder.setOnClickListener(v -> {
            if(Objects.equals(sortOrder, "asc")) {
                sortOrder = "desc";
                binding.tvOrder.setText("Sắp xếp: Giảm dần");
                searchData(binding, currentKeyword, true);
                return;
            }
            sortOrder = "asc";
            binding.tvOrder.setText("Sắp xếp: Tăng dần");
            searchData(binding, currentKeyword, true);
        });
        // Đặt lại
        binding.tvResetOrder.setOnClickListener(v -> {
            sortOrder = "";
            binding.tvOrder.setText("Sắp xếp: Không");
            binding.relatedTo.performClick();
        });
        // Add scroll listener for pagination
        binding.rvSearchResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading && !isLastPage) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        // Load more data
                        loadMoreData();
                    }
                }
            }
        });

        searchResultViewModel = new ViewModelProvider(this).get(SearchResultViewModel.class);

        String keyword = getIntent().getStringExtra("keyword");
        if (keyword != null) {
            binding.etSearch.setText(keyword);
            currentKeyword = keyword;
            searchData(binding, keyword, true);
        }

        searchResultViewModel.getIsLoading().observe(this, loading -> {
            if (loading != null) {
                isLoading = loading;
                if (loading) {
                    binding.loadingOverlay.setVisibility(View.VISIBLE);
                } else {
                    binding.loadingOverlay.setVisibility(View.GONE);
                }
            }
        });

        searchResultViewModel.getSearchResults().observe(this, products -> {
            if (products != null) {
                if (currentPage == 1) {
                    // First page or new search
                    if (products.isEmpty()) {
                        binding.tvNotFound.setText("Không tìm thấy sản phẩm");
                        productList.clear();
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    binding.tvNotFound.setText("");
                    productList.clear();
                    productList.addAll(products);
                    adapter.notifyDataSetChanged();
                } else {
                    // Load more data
                    if (products.isEmpty()) {
                        isLastPage = true;
                    } else {
                        int startPosition = productList.size();
                        productList.addAll(products);
                        adapter.notifyItemRangeInserted(startPosition, products.size());
                    }
                }

                // Check if this might be the last page (assuming 10 items per page)
                if (products.size() < 10) {
                    isLastPage = true;
                }
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

                    String keyword = binding.etSearch.getText().toString().trim();
                    currentKeyword = keyword; // Allow empty keyword for general search

                    if (!keyword.isEmpty()) {
                        DataLocalManager.setSearchHistory(keyword);
                    }
                    searchData(binding, keyword, true);
                    binding.etSearch.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }

    private void searchData(ActivitySearchResultBinding binding, String keyword, boolean isNewSearch) {
        if (isNewSearch) {
            currentPage = 1;
            isLastPage = false;
            binding.tvNotFound.setText("");
        }
        searchResultViewModel.getSearchResults(keyword, "", "", sortBy, sortOrder, currentPage, 10);
    }

    private void loadMoreData() {
        if (!isLoading && !isLastPage) {
            currentPage++;
            searchResultViewModel.getSearchResults(currentKeyword, "", "", sortBy, sortOrder, currentPage, 10);
        }
    }
}