package com.medsec;

public class AuthenticationException extends Exception {
    public static final String BAD_CREDENTIALS = "Bad credentials";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String TOKEN_EXPIRED = "Token expired";

    public AuthenticationException() {
        super(BAD_CREDENTIALS);
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
