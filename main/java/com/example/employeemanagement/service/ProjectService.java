package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    Project findById(Long id);
    Project save(Project project);
    void delete(Project project);
}