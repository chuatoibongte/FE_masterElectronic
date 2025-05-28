package com.example.mater_electronic.ui.navigation.my_cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.target.BitmapThumbnailImageViewTarget;
import com.example.mater_electronic.database.cart.CartManager;
import com.example.mater_electronic.databinding.FragmentMycartBinding;
import com.example.mater_electronic.models.ProductItem;
import com.example.mater_electronic.models.cart.CartItem;
import com.example.mater_electronic.ui.activity.checkout.ActivityCheckout;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {
    private CartManager cartManager;
    private MyCartViewModel myCartViewModel;
    private RecyclerView rcvCart;
    private MyCartAdapter myCartAdapter;
    private FragmentMycartBinding binding;

    private Button btnmuangay;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // tạo view model cho cart
        myCartViewModel = new ViewModelProvider(this).get(MyCartViewModel.class);
        binding = FragmentMycartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rcvCart = binding.cartItemsList;
        myCartAdapter = new MyCartAdapter(getContext());

        cartManager = new CartManager(getContext(), getCurrentUserId());
        myCartAdapter.setCartManager(cartManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvCart.setLayoutManager(linearLayoutManager);

        // myCartAdapter.setData(getCartItems()); // set data cho recycler view (list các CartItem
        myCartViewModel.getCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            myCartAdapter.setData(cartItems);
        });

        binding.cartItemsList.setAdapter(myCartAdapter);

        btnmuangay = binding.mycartMuangayButton;


        btnmuangay.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ActivityCheckout.class);
            startActivity(intent);
        });

        return root;

    }


    private String getCurrentUserId() {
        SharedPreferences prefs = getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("_id", "");
    }
    private List<CartItem> getCartItems() {
        return new ArrayList<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}