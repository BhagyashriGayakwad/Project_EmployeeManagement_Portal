package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> findAll();
    Admin findById(Long id);
    Admin save(Admin admin);
    void delete(Admin admin);
}
