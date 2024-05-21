package com.example.employeemanagement.controller;


import com.example.employeemanagement.service.ManagerService;
import org.apache.catalina.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping
    public List<Manager> getAllManagers() {
//        return managerService.findAll();

    return null;}

    @PostMapping
    public Manager createManager(@RequestBody Manager manager) {
//        return managerService.save(manager);
        return null;
    }

    // Additional methods for updating, deleting, and managing projects
}