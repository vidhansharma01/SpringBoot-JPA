package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Greeting {
    long id;
    String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
