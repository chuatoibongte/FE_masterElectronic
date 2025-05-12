package com.example.mater_electronic.ui.navigation.my_cart;

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

import com.example.mater_electronic.databinding.FragmentMycartBinding;
import com.example.mater_electronic.models.ProductItem;
import com.example.mater_electronic.models.cart.CartItem;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {
    private RecyclerView rcvCart;
    private MyCartAdapter myCartAdapter;
    private FragmentMycartBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // tạo view model cho cart
        MyCartViewModel myCartViewModel = new ViewModelProvider(this).get(MyCartViewModel.class);
        binding = FragmentMycartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rcvCart = binding.cartItemsList;
        myCartAdapter = new MyCartAdapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvCart.setLayoutManager(linearLayoutManager);

        // myCartAdapter.setData(getCartItems()); // set data cho recycler view (list các CartItem
        myCartViewModel.getCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            myCartAdapter.setData(cartItems);
        });


        binding.cartItemsList.setAdapter(myCartAdapter);

        binding.myCartBuynow.setOnClickListener(v-> {
            List<CartItem> cartList = new ArrayList<>();

            for (int i = 1; i <= 3; i++) {
                String name = "BCS loại " + i;
                double price = i * 100.0;
                int quantity = i;

                CartItem item = new CartItem(name, price, quantity);
                cartList.add(item);
            }
        });

        return root;
    }

    private List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            cartItems.add(new CartItem("https://res.cloudinary.com/dvtcbryg5/image/upload/v1746154983/ElectronicMaster/ElectronicImages/kt6zkcwyzas9e7nrcvem.jpg", "Sản phẩm " + i, 100.0 * (i + 1), 1, "main category"));
        }
        return cartItems;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}