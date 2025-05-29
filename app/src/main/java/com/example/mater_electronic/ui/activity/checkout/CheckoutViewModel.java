package com.example.mater_electronic.ui.activity.checkout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mater_electronic.database.cart.CartManager;
import com.example.mater_electronic.models.account.Address;
import com.example.mater_electronic.models.cart.CartItem;
import com.example.mater_electronic.models.checkout.CheckoutItem;
import com.example.mater_electronic.models.checkout.CreateOrderRequest;
import com.example.mater_electronic.models.checkout.CreateOrderResponse;
import com.example.mater_electronic.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutViewModel extends AndroidViewModel {
    private OrderRepository orderRepository = new OrderRepository();
    private CartManager cartManager;
    private MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<String> resultMessage = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<String> getResultMessage() {
        return resultMessage;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }
    public CheckoutViewModel(@NonNull Application application) {
        super(application);
        String userId = getCurrentUserId();
        cartManager = new CartManager(application.getApplicationContext(), userId);
        loadCartItems();
    }
    private void loadCartItems() {
        new Thread(() -> {
            List<CartItem> items = cartManager.getSelectedCartItems();
            cartItems.postValue(items);
        }).start();
    }
    private String getCurrentUserId() {
        SharedPreferences prefs = getApplication().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("_id", "");
    }
    private String getCurrentUserAccessToken() {
        SharedPreferences prefs = getApplication().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("accessToken", null);
    }
    public void createOrder(String accessToken, String note, String paymentMethod, Address address, List<CheckoutItem> listElectronics) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(
                note,
                paymentMethod,
                address,
                listElectronics
        );
        orderRepository.createOrder(accessToken, createOrderRequest, new Callback<CreateOrderResponse>() {
            @Override
            public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        resultMessage.setValue("Tạo đơn hàng thành công");
                    } else {
                        resultMessage.setValue("Tạo đơn hàng thất bại");
                    }
                } else {
                    resultMessage.setValue("Lỗi server: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CreateOrderResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
            }
        });
    }
}
