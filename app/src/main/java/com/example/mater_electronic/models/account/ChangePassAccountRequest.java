package com.example.mater_electronic.models.account;

public class ChangePassAccountRequest {
    public ChangePassAccountRequest(String password, String newpassword) {
        this.password = password;
        this.newpassword = newpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public String getPassword() {
        return password;
    }

    public String password;
    public String newpassword;

}
