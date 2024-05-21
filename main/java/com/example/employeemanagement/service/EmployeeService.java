package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(Long id);
    Employee save(Employee employee);
    void delete(Employee employee);
}