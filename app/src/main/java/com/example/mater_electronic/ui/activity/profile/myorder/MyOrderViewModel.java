package com.example.mater_electronic.ui.activity.profile.myorder;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mater_electronic.models.displaydata.GetElectronicByIdResponse;
import com.example.mater_electronic.models.myorder.GetOrderResponse;
import com.example.mater_electronic.models.myorder.Order;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.repositories.OrderRepository;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderViewModel extends AndroidViewModel {
    private OrderRepository orderRepository = new OrderRepository();
    private MutableLiveData<List<Order>> orderLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<List<Order>> getOrderByUserIDandStatus() {
        return orderLiveData;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public MyOrderViewModel(@NonNull Application application) {
        super(application);
    }
    public void getOrderByUserIDandStatus(String accessToken, String status) {
        orderRepository.getOrderByUserIDandStatus(accessToken, status, new Callback<GetOrderResponse>() {
            @Override
            public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
                Log.e("MyOrderViewModel", "response body raw: " + new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    GetOrderResponse getOrderResponse = response.body();
                    if (getOrderResponse != null && getOrderResponse.isSuccess()) {

                        List<Order> orderList = getOrderResponse.getOrders();
                        orderLiveData.setValue(orderList);
                    } else {
                        errorMessage.setValue("Không thể tải order của bạn");
                    }
                }
            }

            @Override
            public void onFailure(Call<GetOrderResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi:" + t.getMessage());
            }
        });
    }
}
