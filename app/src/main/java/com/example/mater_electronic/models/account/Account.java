package com.example.mater_electronic.models.account;

import java.util.List;

public class Account {
    private String _id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String gender;
    private Avatar avatar;
    private String role;
    private List<Address> addressList;
    private String createdAt;
    private String updatedAt;
    private int __v;
    public String getUsername() {
        return username;
    }
}
