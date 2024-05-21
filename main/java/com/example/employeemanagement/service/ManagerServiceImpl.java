package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Manager;
import com.example.employeemanagement.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager findById(Long id) {
        return managerRepository.findById(id).orElse(null);
    }

    @Override
    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public void delete(Manager manager) {
        managerRepository.delete(manager);
    }
}