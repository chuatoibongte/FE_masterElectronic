package com.example.mater_electronic.ui.activity.searchresult;

import android.os.Bundle;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.mater_electronic.R;
import com.example.mater_electronic.databinding.ActivitySearchResultBinding;
import com.example.mater_electronic.models.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchResultBinding binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> finish());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // 2 cột
        binding.rvSearchResult.setLayoutManager(gridLayoutManager);

        List<ProductItem> productList = new ArrayList<>();
        productList.add(new ProductItem("https://res.cloudinary.com/dvtcbryg5/image/upload/v1746154166/ElectronicMaster/ElectronicImages/ovuy9lqg7u1a34kjhiuy.jpg", "Wireless Headphones Bluetooth Style 3 Lavender", 500000, 4.5));
        productList.add(new ProductItem("https://res.cloudinary.com/dvtcbryg5/image/upload/v1746154166/ElectronicMaster/ElectronicImages/ovuy9lqg7u1a34kjhiuy.jpg", "Wireless Headphones Bluetooth Style 3 Lavender", 500000, 4.5));
        productList.add(new ProductItem("https://res.cloudinary.com/dvtcbryg5/image/upload/v1746154166/ElectronicMaster/ElectronicImages/ovuy9lqg7u1a34kjhiuy.jpg", "Wireless Headphones Bluetooth Style 3 Lavender", 500000, 4.5));
        productList.add(new ProductItem("https://res.cloudinary.com/dvtcbryg5/image/upload/v1746154166/ElectronicMaster/ElectronicImages/ovuy9lqg7u1a34kjhiuy.jpg", "Wireless Headphones Bluetooth Style 3 Lavender", 500000, 4.5));
        productList.add(new ProductItem("https://res.cloudinary.com/dvtcbryg5/image/upload/v1746154166/ElectronicMaster/ElectronicImages/ovuy9lqg7u1a34kjhiuy.jpg", "Wireless Headphones Bluetooth Style 3 Lavender", 500000, 4.5));
        productList.add(new ProductItem("https://res.cloudinary.com/dvtcbryg5/image/upload/v1746154166/ElectronicMaster/ElectronicImages/ovuy9lqg7u1a34kjhiuy.jpg", "Wireless Headphones Bluetooth Style 3 Lavender", 500000, 4.5));
        productList.add(new ProductItem("https://res.cloudinary.com/dvtcbryg5/image/upload/v1746154166/ElectronicMaster/ElectronicImages/ovuy9lqg7u1a34kjhiuy.jpg", "Wireless Headphones Bluetooth Style 3 Lavender", 500000, 4.5));
        productList.add(new ProductItem("https://res.cloudinary.com/dvtcbryg5/image/upload/v1746154166/ElectronicMaster/ElectronicImages/ovuy9lqg7u1a34kjhiuy.jpg", "Wireless Headphones Bluetooth Style 3 Lavender", 500000, 4.5));

        SearchResultAdapter adapter = new SearchResultAdapter(productList);
        binding.rvSearchResult.setAdapter(adapter);

    }
}