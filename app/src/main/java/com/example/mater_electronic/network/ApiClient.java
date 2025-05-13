package com.example.mater_electronic.network;

import com.example.mater_electronic.network.account.AccountService;
import com.example.mater_electronic.network.auth.AuthService;
import com.example.mater_electronic.network.product.ProductService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://be-electronic-master.vercel.app/";
    private static Retrofit retrofit;

    public static AuthService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(AuthService.class);
    }
    public static ProductService getProductService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ProductService.class);
    }

//    Account service cần xác thực (Cần accessToken)
    public  static AccountService getAccountService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(AccountService.class);
    }
}