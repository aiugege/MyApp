package com.havefun.leeky.myapp.bean;

public class MessageEvent {

    private String message;

    public MessageEvent() {

    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
