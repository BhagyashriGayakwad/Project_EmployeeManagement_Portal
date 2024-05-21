package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Project;
import com.example.employeemanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Get all projects
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.findAll();
    }

    // Get a single project by ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    // Create a new project
    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.save(project);
    }

    // Update an existing project
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        Project project = projectService.findById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        project.setProjectName(projectDetails.getProjectName());
        project.setManager(projectDetails.getManager());  // Assuming there is a 'manager' field in Project
        final Project updatedProject = projectService.save(project);
        return ResponseEntity.ok(updatedProject);
    }

    // Delete a project
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Project project = projectService.findById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        projectService.delete(project);
        return ResponseEntity.ok().build();
    }
}