package com.example.mater_electronic.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivitySearchFocusBinding;
import com.example.mater_electronic.localdata.DataLocalManager;
import com.example.mater_electronic.ui.activity.searchresult.SearchResultActivity;
import com.example.mater_electronic.ui.activity.searchresult.SearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFocusActivity extends AppCompatActivity {
    private ActivitySearchFocusBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchFocusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());
        // focus thanh search khi mở màn hình này
        binding.etSearch.requestFocus();

        RecyclerView searchHintRecyclerView = binding.rvSearchHint;
        searchHintRecyclerView.setLayoutManager( new LinearLayoutManager(this));

        List<String> searchHints = DataLocalManager.getSearchHistory();
        SearchFocusAdapter searchFocusAdapter = new SearchFocusAdapter(searchHints);
        searchHintRecyclerView.setAdapter(searchFocusAdapter);

        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            /**
             * Handles editor actions for the search input field.
             *
             * This method is called when an action is performed on the search input field,
             * such as pressing the search button on the soft keyboard or pressing the Enter key.
             *
             * If the action is a search action (IME_ACTION_SEARCH), done action (IME_ACTION_DONE),
             * or if the Enter key is pressed, it retrieves the search keyword from the input field,
             * creates an Intent to start the SearchResultActivity, puts the keyword as an extra
             * in the Intent, and starts the activity.
             *
             * @param v The TextView that the action is being performed on (the search input field).
             * @param actionId The ID of the action being performed.
             * @param event The KeyEvent associated with the action, if any.
             * @return True if the action was handled, false otherwise.
             */
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // Toast.makeText(SearchResultActivity.this, binding.etSearch.getText().toString(), Toast.LENGTH_SHORT).show();
                    String keyword = binding.etSearch.getText().toString();
                    DataLocalManager.setSearchHistory(keyword);
                    Intent intent = new Intent(SearchFocusActivity.this, SearchResultActivity.class);
                    intent.putExtra("keyword", keyword);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        List<String> searchHints = DataLocalManager.getSearchHistory();
        SearchFocusAdapter searchFocusAdapter = new SearchFocusAdapter(searchHints);
        binding.rvSearchHint.setAdapter(searchFocusAdapter);
    }
}