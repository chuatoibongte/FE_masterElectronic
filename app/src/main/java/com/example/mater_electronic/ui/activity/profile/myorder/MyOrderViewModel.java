package com.example.mater_electronic.ui.activity.profile.myorder;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mater_electronic.models.displaydata.GetElectronicByIdResponse;
import com.example.mater_electronic.models.myorder.CancelOrderResponse;
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
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<List<Order>> getOrderByUserIDandStatus() {
        return orderLiveData;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public MyOrderViewModel(@NonNull Application application) {
        super(application);
    }
    public void getOrderByUserIDandStatus(String accessToken, String status) {
        isLoading.setValue(true);
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
                    isLoading.setValue(false);
                }
                else {
                    errorMessage.setValue("Lỗi server !!!");
                    isLoading.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<GetOrderResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi:" + t.getMessage());
            }
        });
    }
    public void cancelOrder(String accessToken, String id) {
        isLoading.setValue(true);
        orderRepository.cancelOrder(accessToken, id, new Callback<CancelOrderResponse>() {
            @Override
            public void onResponse(Call<CancelOrderResponse> call, Response<CancelOrderResponse> response) {
                if (response.isSuccessful()) {
                    CancelOrderResponse cancelOrderResponse = response.body();
                    if (cancelOrderResponse != null && cancelOrderResponse.isSuccess()) {
                        Toast.makeText(getApplication(), "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        getOrderByUserIDandStatus(accessToken, "pending");
                        isLoading.setValue(false);
                    }
                    else {
                        Toast.makeText(getApplication(), "Hủy đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                        isLoading.setValue(false);
                    }
                }
                else {
                    Toast.makeText(getApplication(), "Lỗi server !!!", Toast.LENGTH_SHORT).show();
                    isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<CancelOrderResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi:" + t.getMessage());
                isLoading.setValue(false);
            }
        });
    }
    public void receivedOrder(String accessToken, String id) {
        isLoading.setValue(true);
        orderRepository.receivedOrder(accessToken, id, new Callback<CancelOrderResponse>() {
            @Override
            public void onResponse(Call<CancelOrderResponse> call, Response<CancelOrderResponse> response) {
                if (response.isSuccessful()) {
                    CancelOrderResponse cancelOrderResponse = response.body();
                    if (cancelOrderResponse != null && cancelOrderResponse.isSuccess()) {
                        Toast.makeText(getApplication(), "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                        getOrderByUserIDandStatus(accessToken, "in transit");
                        isLoading.setValue(false);
                    }
                    else {
                        Toast.makeText(getApplication(), "Xác nhận thất bại", Toast.LENGTH_SHORT).show();
                        isLoading.setValue(false);
                    }
                }
                else {
                    Toast.makeText(getApplication(), "Lỗi server !!!", Toast.LENGTH_SHORT).show();
                    isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<CancelOrderResponse> call, Throwable t) {

            }
        });
    }
}
