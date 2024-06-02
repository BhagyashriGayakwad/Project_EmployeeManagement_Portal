package com.springboot.emp_mang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MyController {

    @GetMapping("/greeting")
    public String getGreeting() {
        return "Hello, World!";
    }
}
