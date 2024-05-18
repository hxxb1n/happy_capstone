package com.example.happy_app.api;

public class CreateMemberRequest {
    private Long id;
    private String name;
    private String password;

    public CreateMemberRequest(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public CreateMemberRequest(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
