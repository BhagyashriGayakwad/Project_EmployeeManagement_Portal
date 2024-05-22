package com.example.employeemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index"; // Returns index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Returns login.html
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Returns register.html
    }
    @GetMapping("/h")
    public String h(){
        return "h";
    }

}