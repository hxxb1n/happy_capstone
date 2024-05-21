package com.example.happy_app.dto;

import java.time.LocalDateTime;

public class OrderResponseDto {
    private Long orderId;
    private String orderDate;
    private String itemName;
    private int count;
    private int orderPrice;
    private String status;

    public Long getOrderId() {
        return orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getCount() {
        return count;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public String getOrderStatus() {
        return status;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setOrderStatus(String orderStatus) {
        this.status = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
