package com.example.mater_electronic.ui.activity.profile.myorder.deliveredorderitem;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mater_electronic.models.myorder.GetOrderElectronicsResponse;
import com.example.mater_electronic.models.myorder.OrderElectronics;
import com.example.mater_electronic.repositories.OrderRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveredOrderItemViewModel extends AndroidViewModel {
    private OrderRepository orderRepository = new OrderRepository();
    private MutableLiveData<List<OrderElectronics>> orderElectronicsList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public LiveData<List<OrderElectronics>> getOrderElectronicsList() {
        return orderElectronicsList;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public DeliveredOrderItemViewModel(@NonNull Application application) {
        super(application);
    }
    public void getElectronicsByOrderId(String accessToken, String id) {
        Log.e("AccessToken", accessToken);
        Log.e("Order id", id);
        isLoading.setValue(true);
        orderRepository.getElectronicsByOrderID(accessToken, id, new Callback<GetOrderElectronicsResponse>() {
            @Override
            public void onResponse(Call<GetOrderElectronicsResponse> call, Response<GetOrderElectronicsResponse> response) {
                Log.e("Response", response.toString());
                if (response.isSuccessful()) {
                    GetOrderElectronicsResponse getOrder = response.body();
                    Log.e("GetOrderElectronicsResponse", "" + getOrder.isSuccess());
                    if (getOrder != null && getOrder.isSuccess()) {
                        List<OrderElectronics> orderElectronics = getOrder.getElectronics();
                        Log.e("OrderElectronics", orderElectronics.get(0).getElectronicID().getName());
                        orderElectronicsList.setValue(orderElectronics);
                        isLoading.setValue(false);
                    } else {
                        Log.e("Lỗi tiếp", "Không thể tải sản phẩm của đơn hàng");
                        errorMessage.setValue("Không thể tải sản phẩm của đơn hàng");
                        isLoading.setValue(false);
                    }
                    isLoading.setValue(false);
                } else {
                    errorMessage.setValue("Lỗi server !!!");
                    isLoading.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<GetOrderElectronicsResponse> call, Throwable t) {
                errorMessage.setValue("Lỗi: " + t.getMessage());
                isLoading.setValue(false);
            }
        });

    }

}
