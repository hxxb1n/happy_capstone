package com.example.happy_app.api;

import com.example.happy_app.model.OrderDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderApi {
    @POST("/api/order")
    Call<String> createOrder(@Body OrderDto orderDto);
}
