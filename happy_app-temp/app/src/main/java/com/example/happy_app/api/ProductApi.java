package com.example.happy_app.api;

import com.example.happy_app.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApi {

    @GET("/api/items")
    Call<List<Product>> getProducts();

    @GET("/api/item/{id}")
    Call<Product> getProductById(@Path("id") long id);

}
