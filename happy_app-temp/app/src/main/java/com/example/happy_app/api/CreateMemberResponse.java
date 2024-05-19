package com.example.happy_app.api;

import com.example.happy_app.model.Address;

public class CreateMemberResponse {
    private String name;
    private Long id;
    private String authority;
    private Address address;

    public CreateMemberResponse(String name, Long id, String authority, Address address) {
        this.name = name;
        this.id = id;
        this.authority = authority;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }

    public Address getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
