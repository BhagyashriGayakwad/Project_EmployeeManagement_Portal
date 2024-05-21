package com.example.employeemanagement.controller;


import com.example.employeemanagement.model.Admin;
import com.example.employeemanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Get all admins
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.findAll();
    }

    // Get a single admin by ID
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.findById(id);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admin);
    }

    // Create a new admin
    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.save(admin);
    }

    // Update an existing admin
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin adminDetails) {
        Admin existingAdmin = adminService.findById(id);
        if (existingAdmin == null) {
            return ResponseEntity.notFound().build();
        }
        existingAdmin.setName(adminDetails.getName());
        existingAdmin.setEmail(adminDetails.getEmail());
        Admin updatedAdmin = adminService.save(existingAdmin);
        return ResponseEntity.ok(updatedAdmin);
    }

    // Delete an admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        Admin admin = adminService.findById(id);
        if (admin == null) {
            return ResponseEntity.notFound().build();
        }
        adminService.delete(admin);
        return ResponseEntity.ok().build();
    }
}