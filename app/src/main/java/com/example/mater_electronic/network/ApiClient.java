package com.example.mater_electronic.network;

import com.example.mater_electronic.network.account.AccountService;
import com.example.mater_electronic.network.auth.AuthService;
import com.example.mater_electronic.network.favorite.FavoriteService;
import com.example.mater_electronic.network.order.OrderService;
import com.example.mater_electronic.network.product.ProductService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://13.239.123.42/backend/";
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
    public static OrderService getOrderService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(OrderService.class);
    }

    //Favorite service
    public static FavoriteService getFavouriteService(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit.create(FavoriteService.class);
    }
}