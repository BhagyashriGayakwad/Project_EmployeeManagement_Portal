package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.UserRegistrationDto;
import com.example.employeemanagement.dto.UserRegistrationDto;
import com.example.employeemanagement.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    User save(UserRegistrationDto registrationDto);
}
