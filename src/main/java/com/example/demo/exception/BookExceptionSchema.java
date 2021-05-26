package com.example.demo.exception;

public class BookExceptionSchema {
    private String message;
    private String id;

    public BookExceptionSchema() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
