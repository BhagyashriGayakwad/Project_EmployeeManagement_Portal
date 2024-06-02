package com.springboot.emp_mang.controller;


import com.springboot.emp_mang.dto.ProjectDetails;
import com.springboot.emp_mang.dto.ProjectRequest;
import com.springboot.emp_mang.dto.UserRequest;
import com.springboot.emp_mang.entities.Project;
import com.springboot.emp_mang.entities.RequestResource;
import com.springboot.emp_mang.entities.User;

import com.springboot.emp_mang.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-user")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        try {
            adminService.registerUser(userRequest);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createProject")
    public ResponseEntity<?> addProject(@RequestBody ProjectRequest projectRequest) {
        try {
            adminService.createProject(projectRequest);
            return ResponseEntity.ok(true);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assignProject/{projectId}/{employeeId}")
    public ResponseEntity<?> assignProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        try {
            adminService.assignProject(projectId, employeeId);
            return ResponseEntity.ok(true);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/unassignProject/{projectId}/{employeeId}")
    public ResponseEntity<?> unassignProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        try {
            adminService.unassignProject(projectId, employeeId);
            return ResponseEntity.ok(true);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projects")
    public ResponseEntity<?> getProjects() {
        try {
            List<Project> projects = adminService.getAllProjects();
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/employees")
    public ResponseEntity<?> getEmployees() {
        try {
            List<User> employees = adminService.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/managers")
    public ResponseEntity<?> getManagers() {
        try {
            List<User> managers = adminService.getAllManagers();
            return ResponseEntity.ok(managers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        try {
            List<User> users=adminService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/projectDetails")
    public ResponseEntity<List<ProjectDetails>> getProjectDetails() {
        List<ProjectDetails> projectDetails = adminService.getAllProjectDetails();
        return ResponseEntity.ok(projectDetails);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteEmployees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            adminService.deleteEmployee(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateEmployee/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long employeeId, @RequestBody UserRequest userRequest) {
        try {
            adminService.updateEmployee(employeeId, userRequest);
            return ResponseEntity.ok("Employee details updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/requests")
    public ResponseEntity<List<RequestResource>> getAllResourceRequests() {
        List<RequestResource> requests = adminService.getAllResourceRequests();
        return ResponseEntity.ok(requests);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/requests/{requestId}/approve")
    public ResponseEntity<RequestResource> approveResourceRequest(@PathVariable Long requestId) {
        try {
            RequestResource approvedRequest = adminService.approveResourceRequest(requestId);
            return ResponseEntity.ok(approvedRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/requests/{requestId}/reject")
    public ResponseEntity<RequestResource> rejectResourceRequest(@PathVariable Long requestId) {
        try {
            RequestResource rejectedRequest = adminService.rejectResourceRequest(requestId);
            return ResponseEntity.ok(rejectedRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}