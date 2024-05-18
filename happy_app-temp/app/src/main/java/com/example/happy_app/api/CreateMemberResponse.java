package com.example.happy_app.api;

public class CreateMemberResponse {
    private String name;
    private Long id;
    private String authority;

    public CreateMemberResponse(String name, Long id, String authority) {
        this.name = name;
        this.id = id;
        this.authority = authority;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
