package com.itacit.healthcare.global.errors;

/**
 * Created by root on 02.11.15.
 */
public class AuthError {
    private String message;

    public AuthError(String message) {
        this.message = message;
    }

    public AuthError() {

    }

    public String getMessage() {
        return message;
    }
}
