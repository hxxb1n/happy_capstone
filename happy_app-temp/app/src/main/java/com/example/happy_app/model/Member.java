package com.example.happy_app.model;

public class Member {
    private long id;
    private String name;
    private Address address;
    private String authority;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getAuthority() {
        return authority;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
