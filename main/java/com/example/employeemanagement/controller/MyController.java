package com.example.employeemanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping
    public String print(){
        return "Hello Indore, when Bageshwar Dham Sarkar Will Come in your City Inform me Because i am a big fan of him...";
    }


}
