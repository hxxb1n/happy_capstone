package com.example.happy_app.model;

public class ProfileUpdateRequest {
    private long memberId;
    private Address address;

    public ProfileUpdateRequest() {
    }

    public ProfileUpdateRequest(long memberId, Address address) {
        this.memberId = memberId;
        this.address = address;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
