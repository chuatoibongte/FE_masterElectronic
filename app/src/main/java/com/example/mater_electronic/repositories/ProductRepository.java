package com.example.mater_electronic.repositories;

import com.example.mater_electronic.models.displaydata.GetAllCategoryResponse;
import com.example.mater_electronic.models.displaydata.GetElectronicByIdResponse;
import com.example.mater_electronic.models.displaydata.GetSearchResultResponse;
import com.example.mater_electronic.models.product.Product;
import com.example.mater_electronic.models.review.GetReviewResponse;
import com.example.mater_electronic.network.ApiClient;
import com.example.mater_electronic.network.product.ProductService;

import java.util.List;

import retrofit2.Callback;

public class ProductRepository {

    private ProductService productServiceApi = ApiClient.getProductService();

    // Lấy tất cả sản phẩm
    public void getAllProducts(Callback<List<Product>> callback) {
        productServiceApi.getAllProducts().enqueue(callback);
    }

    // Lấy chi tiết sản phẩm theo ID
    public void getProductDetail(String productId, Callback<GetElectronicByIdResponse> callback) {
        productServiceApi.getProductDetail(productId).enqueue(callback);
    }

    // Lấy review theo ID sản phẩm
    public void getReviews(String productId, Callback<GetReviewResponse> callback) {
        productServiceApi.getReviews(productId).enqueue(callback);
    }

    public void getSearchResults(String keyword, String slugCates, String brandNames, String sortBy, String sortOrder, int page, int limit, Callback<GetSearchResultResponse> callback) {
        productServiceApi.getSearchResults(keyword, slugCates, brandNames, sortBy, sortOrder, page, limit).enqueue(callback);
    }

    // Lấy danh sách sản phẩm theo danh mục
    public void getProductsByCategory(String category, Callback<List<Product>> callback) {
        productServiceApi.getProductsByCategory(category).enqueue(callback);
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(String productId, int quantity, Callback<Void> callback) {
        productServiceApi.addToCart(productId, quantity).enqueue(callback);
    }
    public void getAllCategories(Callback<GetAllCategoryResponse> callback) {
        productServiceApi.getAllCategories().enqueue(callback);
    }
}