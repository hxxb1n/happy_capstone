package com.example.happy_app.api;

import com.example.happy_app.dto.MemberRequestDto;
import com.example.happy_app.dto.OrderRequestDto;
import com.example.happy_app.dto.OrderResponseDto;
import com.example.happy_app.dto.ParcelDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/myOrder")
    Call<List<OrderResponseDto>> getOrders(@Body MemberRequestDto memberRequestDto);

    @POST("/api/myOrderParcelTrackingNumber")
    Call<ParcelDto> getParcelTrackingNumber(@Body OrderRequestDto orderRequestDto);

    @POST("/api/orderCancel/{orderId}")
    Call<String> cancelOrder(@Path("orderId") Long orderId);

}
