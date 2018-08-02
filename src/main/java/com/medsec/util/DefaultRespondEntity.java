package com.medsec.util;

public class DefaultRespondEntity {
    private String message;

    public DefaultRespondEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DefaultRespondEntity message(final String message) {
        setMessage(message);
        return this;
    }

}
