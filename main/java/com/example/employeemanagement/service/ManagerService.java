package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Manager;

import java.util.List;

public interface ManagerService {
    List<Manager> findAll();
    Manager findById(Long id);
    Manager save(Manager manager);
    void delete(Manager manager);
}