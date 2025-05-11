package com.example.mater_electronic.network.product;

import com.example.mater_electronic.models.displaydata.GetAllCategoryResponse;
import com.example.mater_electronic.models.displaydata.GetElectronicByIdResponse;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.models.review.GetReviewResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {

    // Lấy danh sách tất cả sản phẩm
    @GET("/product/getAll")
    Call<List<Product>> getAllProducts();

    // Lấy chi tiết sản phẩm theo ID
    @GET("/user/displayData/electronic/{id}")
    Call<GetElectronicByIdResponse> getProductDetail(@Path("id") String productId);

    // Lấy đánh giá theo ID sản phẩm
    @GET("/user/displayData/commentsByElectronic/{id}")
    Call<GetReviewResponse> getReviews(@Path("id") String productId);

    // Lấy danh sách sản phẩm theo danh mục (ví dụ: "RAM Máy Tính")
    @GET("/product/getByCategory")
    Call<List<Product>> getProductsByCategory(@Query("category") String category);

    // Nếu bạn muốn thêm sản phẩm vào giỏ
    @GET("/cart/add")
    Call<Void> addToCart(@Query("productId") String productId, @Query("quantity") int quantity);

    // Lấy tất cả category
    @GET("/user/displayData/getAllCate")
    Call<GetAllCategoryResponse> getAllCategories();
}