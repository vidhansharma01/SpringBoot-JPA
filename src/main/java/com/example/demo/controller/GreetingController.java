package com.example.demo.controller;

import com.example.demo.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @Autowired
    Greeting greeting;

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name") String name){
        greeting.setId(1);
        greeting.setContent("This is " + name);
        return greeting;
    }
}
