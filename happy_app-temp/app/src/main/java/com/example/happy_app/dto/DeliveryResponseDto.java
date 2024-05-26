package com.example.happy_app.dto;

import androidx.annotation.NonNull;

public class DeliveryResponseDto {
    private Long orderId;
    private String city;
    private String street;
    private String zip;
    private String status;
    private String trackingNumber;

    // Getters and setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public DeliveryResponseDto(Long orderId, String city, String street, String zip, String status, String trackingNumber) {
        this.orderId = orderId;
        this.city = city;
        this.street = street;
        this.zip = zip;
        this.status = status;
        this.trackingNumber = trackingNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return "DeliveryResponseDto{" +
                "orderId=" + orderId +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", status='" + status + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                '}';
    }
}
