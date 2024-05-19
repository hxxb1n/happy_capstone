package com.example.happy_app.model;

public class OrderDto {
    private long memberId;
    private long itemId;
    private int count;

    public OrderDto(long memberId, long itemId, int count) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.count = count;
    }

    // Getters and Setters
    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
