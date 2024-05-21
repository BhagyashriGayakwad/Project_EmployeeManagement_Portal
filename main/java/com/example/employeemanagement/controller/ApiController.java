package com.example.employeemanagement.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
public class ApiController {


    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
