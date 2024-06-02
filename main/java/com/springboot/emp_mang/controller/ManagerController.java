package com.springboot.emp_mang.controller;

import com.springboot.emp_mang.dto.RequestResource;
import com.springboot.emp_mang.dto.UserRequest;
import com.springboot.emp_mang.entities.Project;
import com.springboot.emp_mang.entities.User;
import com.springboot.emp_mang.repository.ProjectRepository;
import com.springboot.emp_mang.repository.UserRepository;
import com.springboot.emp_mang.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update-profile/{userId}")
    public ResponseEntity<String> updateUserProfile(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        try {
            boolean isUpdated = managerService.updateUserProfile(userId, userRequest);
            if (isUpdated) {
                return ResponseEntity.ok("User profile updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unknown error");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/employees/filter")
    public ResponseEntity<List<User>> filterEmployeesBySkills(@RequestParam("skills") List<String> skills) {
        List<User> filteredEmployees = managerService.filterEmployeesBySkills(skills);
        return ResponseEntity.ok(filteredEmployees);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/filter/unassigned-employees")
    public ResponseEntity<List<User>> getUnassignedEmployees() {
        List<User> unassignedEmployees = managerService.getUnassignedEmployees();
        return ResponseEntity.ok(unassignedEmployees);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/employees")
    public ResponseEntity<List<User>> getEmployees() {
        List<User> employees = userRepository.findByRole(User.Role.EMPLOYEE);
        return ResponseEntity.ok(employees);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/managers")
    public ResponseEntity<List<User>> getManagers() {
        List<User> managers = userRepository.findByRole(User.Role.MANAGER);
        return ResponseEntity.ok(managers);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projects = projectRepository.findAll();
        return ResponseEntity.ok(projects);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/request-resources")
    public ResponseEntity<List<com.springboot.emp_mang.entities.RequestResource>> createRequestResources(@RequestBody RequestResource requestResource) {
        try {
            System.out.println("Received request: " + requestResource);
            List<com.springboot.emp_mang.entities.RequestResource> requestResources = managerService.createRequestResources(requestResource);
            return ResponseEntity.ok(requestResources);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
