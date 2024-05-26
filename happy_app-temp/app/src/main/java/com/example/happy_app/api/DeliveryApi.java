package com.example.happy_app.api;

import com.example.happy_app.dto.DeliveryResponseDto;
import com.example.happy_app.dto.ParcelDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DeliveryApi {

    @POST("/api/deliveryOrder")
    Call<List<DeliveryResponseDto>> getAllDeliveryOrders();

    @POST("/api/enter")
    Call<Boolean> enter(@Body ParcelDto parcelDto);

}
