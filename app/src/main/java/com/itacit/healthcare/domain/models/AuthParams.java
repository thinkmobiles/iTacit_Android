package com.itacit.healthcare.domain.models;

/**
 * Created by root on 19.11.15.
 */
public class AuthParams {
    private String userName;
    private String password;

    public AuthParams(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
