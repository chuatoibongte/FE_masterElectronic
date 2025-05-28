package com.example.mater_electronic.ui.navigation.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mater_electronic.R;
import com.example.mater_electronic.database.AccountDatabase;
import com.example.mater_electronic.databinding.FragmentHomeBinding;
import com.example.mater_electronic.models.account.Account;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.ui.activity.detail.ProductDetailActivity;
import com.example.mater_electronic.ui.activity.login.LoginActivity;
import com.example.mater_electronic.ui.activity.register.Register;
import com.example.mater_electronic.ui.activity.search.SearchFocusActivity;
import com.example.mater_electronic.ui.navigation.search.SearchActivity;
import com.example.mater_electronic.models.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Handler handler;
    private Runnable autoScrollRunnable;
    private HomeProductAdapter adapter;
    private List<Product> productList;
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel dashboardViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Lấy accessToken từ SharedPreferences
        SharedPreferences prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("accessToken", null);
        //Lấy _id từ SharePreferences
        String _id = prefs.getString("_id", null);
        //Lấy User từ DB
        //Kiểm tra người dùng có đăng nhập chưa thông quá accessToken
        if (accessToken != null && !accessToken.isEmpty() && _id != null && !_id.isEmpty()) {
            Account account = AccountDatabase.getInstance(getContext()).accountDAO().getAccountById(_id);
            //Ẩn nút đăng ký đăng nhập vì người dùng đã đăng nhập
            if (account != null) {
                // Đã đăng nhập và tìm thấy tài khoản
                binding.homeLogin.setVisibility(View.GONE);
                binding.homeRegister.setVisibility(View.GONE);
                binding.tvWelcome.setText("Hi, Welcome " + account.getUsername());
            } else {
                // Đã có accessToken nhưng không tìm thấy tài khoản trong DB
                binding.homeLogin.setVisibility(View.VISIBLE);
                binding.homeRegister.setVisibility(View.VISIBLE);
                binding.tvWelcome.setText("Welcome! Please register or log in.");
            }
        } else {
            //Hiện nút vì người dùng chưa đăng nhập
            binding.homeLogin.setVisibility(View.VISIBLE);
            binding.homeRegister.setVisibility(View.VISIBLE);
        }

        // Sự kiện Đăng Nhập
        binding.homeLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });

        // Sự kiện Đăng Ký
        binding.homeRegister.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Register.class);
            startActivity(intent);
        });

        // Sự kiện Search chuyển sang Activity
        binding.homeSearch.setOnClickListener(v -> {
            // Intent intent = new Intent(getContext(), ProductDetailActivity.class);
            Intent intent = new Intent(getContext(), SearchFocusActivity.class);
            startActivity(intent);
        });

        // Thiết lập Banner ViewPager2
        List<Home_mainevent_BannerItem> bannerItems = new ArrayList<>();
        bannerItems.add(new Home_mainevent_BannerItem(R.drawable.image_testhome_mainevent)); // Ảnh 1
        bannerItems.add(new Home_mainevent_BannerItem(R.drawable.image_testhome_mainevent2)); // Ảnh 2
        bannerItems.add(new Home_mainevent_BannerItem(R.drawable.image_testhome_mainevent)); // Ảnh 3
        bannerItems.add(new Home_mainevent_BannerItem(R.drawable.image_testhome_mainevent2)); // Ảnh 4
        bannerItems.add(new Home_mainevent_BannerItem(R.drawable.image_testhome_mainevent)); // Ảnh 5

        Home_mainevent_BannerAdapter bannerAdapter = new Home_mainevent_BannerAdapter(bannerItems);
        ViewPager2 viewPager = binding.homeMainevent;
        viewPager.setAdapter(bannerAdapter);

        // Đặt vị trí ban đầu ở giữa để hỗ trợ vòng lặp vô hạn
        if (!bannerItems.isEmpty()) {
            int middlePosition = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % bannerItems.size());
            viewPager.setCurrentItem(middlePosition, false);
        }

        // Thiết lập tự động chuyển ảnh với kiểm tra tương tác của người dùng
        handler = new Handler();
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = (currentItem == bannerItems.size() - 1) ? 0 : currentItem + 1;
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 6000); // 6 giây
            }
        };

        // Lắng nghe sự kiện thay đổi trang để tạm dừng auto scroll nếu người dùng tương tác
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                handler.removeCallbacks(autoScrollRunnable);
                handler.postDelayed(autoScrollRunnable, 6000); // Đặt lại auto scroll sau khi người dùng ngừng tương tác
            }
        });

        startAutoScroll();

        // Khởi tạo danh sách sản phẩm
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2); // 2 cột
        binding.rvHomeProduct.setLayoutManager(gridLayoutManager);

        productList = new ArrayList<>();
        adapter = new HomeProductAdapter(productList);
        binding.rvHomeProduct.setAdapter(adapter);

        binding.rvHomeProduct.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
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
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getIsLoading().observe(getViewLifecycleOwner(), loading -> {
            if (loading != null) {
                isLoading = loading;
                if (loading) {
                    binding.loadingOverlay.setVisibility(View.VISIBLE);
                } else {
                    binding.loadingOverlay.setVisibility(View.INVISIBLE);
                }
            }
        });
        homeViewModel.getHomeProducts().observe(getViewLifecycleOwner(), products ->{
            if (products != null) {
                if (currentPage == 1) {
                    // First page or new search
                    if (products.isEmpty()) {
                        productList.clear();
                        adapter.notifyDataSetChanged();
                        return;
                    }
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

        homeViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        loadMoreData();
        return root;
    }
    private void loadMoreData() {
        if (!isLoading && !isLastPage) {
            currentPage++;
            homeViewModel.getHomeProducts(currentPage, 10);
        }
    }

    private void startAutoScroll() {
        handler.postDelayed(autoScrollRunnable, 6000); // Bắt đầu sau 6 giây
    }

    private void stopAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll(); // Dừng khi Fragment không hiển thị
    }

    @Override
    public void onResume() {
        super.onResume();
        startAutoScroll(); // Tiếp tục khi Fragment hiển thị lại
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopAutoScroll(); // Dọn dẹp Handler
        binding = null;
    }
}