package com.example.happy_app.dto;

public class OrderRequestDto {
    private Long orderId;

    public OrderRequestDto(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
