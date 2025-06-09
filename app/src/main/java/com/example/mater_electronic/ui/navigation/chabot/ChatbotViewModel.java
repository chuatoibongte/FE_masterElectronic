package com.example.mater_electronic.ui.navigation.chabot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.chatbot.ChatbotTextRequest;
import com.example.mater_electronic.models.chatbot.ChatbotTextResponse;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.repositories.ChatbotRespository;
import com.example.mater_electronic.repositories.ProductRepository;
import com.example.mater_electronic.models.displaydata.GetElectronicByIdResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatbotViewModel extends ViewModel {
    private final MutableLiveData<Product> productLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Product>> productList = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final ProductRepository repository = new ProductRepository();
    private final ChatbotRespository chatbotRespository = new ChatbotRespository();

    // Getters for LiveData
    public LiveData<Product> getProductLiveData() {
        return productLiveData;
    }

    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void getProductById(String productId) {
        repository.getProductDetail(productId, new Callback<GetElectronicByIdResponse>() {
            @Override
            public void onResponse(Call<GetElectronicByIdResponse> call, Response<GetElectronicByIdResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    productLiveData.postValue(response.body().getData());
                } else {
                    errorMessage.postValue("Không thể tải thông tin sản phẩm");
                }
            }

            @Override
            public void onFailure(Call<GetElectronicByIdResponse> call, Throwable t) {
                productLiveData.postValue(null);
                errorMessage.postValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Get products from chatbot API
    public void getTextChatbot(String query) {
        errorMessage.setValue(null);

        ChatbotTextRequest request = new ChatbotTextRequest(query);
        chatbotRespository.getTextChatbot(request, new Callback<ChatbotTextResponse>() {
            @Override
            public void onResponse(Call<ChatbotTextResponse> call, Response<ChatbotTextResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body().getData();
                    if (products != null && !products.isEmpty()) {
                        productList.postValue(products);
                    } else {
                        errorMessage.postValue("Không tìm thấy sản phẩm phù hợp với yêu cầu của bạn");
                    }
                } else {
                    errorMessage.postValue("Lỗi server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ChatbotTextResponse> call, Throwable t) {
                errorMessage.postValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void clearError() {
        errorMessage.setValue(null);
    }
}