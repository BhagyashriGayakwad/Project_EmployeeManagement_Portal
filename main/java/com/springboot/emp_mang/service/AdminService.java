package com.springboot.emp_mang.service;

import java.util.List;
import java.util.stream.Collectors;

import com.springboot.emp_mang.dto.ProjectDetails;
import com.springboot.emp_mang.dto.ProjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.emp_mang.dto.UserRequest;
import com.springboot.emp_mang.entities.Assignment;
import com.springboot.emp_mang.entities.Project;
import com.springboot.emp_mang.entities.RequestResource;
import com.springboot.emp_mang.entities.RequestResource.RequestStatus;
import com.springboot.emp_mang.entities.User;
import com.springboot.emp_mang.repository.AssignmentRepository;
import com.springboot.emp_mang.repository.ProjectRepository;
import com.springboot.emp_mang.repository.RequestResourceRepository;
import com.springboot.emp_mang.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private  RequestResourceRepository requestResourceRepository;


    public void registerUser(UserRequest userRequest) throws Exception {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new Exception("Email is already registered");
        }

        User user = new User();
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(User.Role.valueOf(userRequest.getRole().toUpperCase()));
        userRepository.save(user);
    }

    public Project createProject(ProjectRequest projectRequest) {
        User manager = userRepository.findByUserIdAndRole(projectRequest.getManagerId(), User.Role.MANAGER)
                .orElseThrow(() -> new IllegalArgumentException("This ID does not belong to a manager."));

        Project project = new Project();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setManager(manager);

        return projectRepository.save(project);
    }
    public void assignProject(Long projectId, Long employeeId) {
        List<Assignment> existingAssignments = assignmentRepository.findByEmployeeUserId(employeeId);
        if (!existingAssignments.isEmpty()) {
            throw new IllegalArgumentException("This employee is already assigned to a project.");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found."));

        User employee = userRepository.findByUserIdAndRole(employeeId, User.Role.EMPLOYEE)
                .orElseThrow(() -> new IllegalArgumentException("This ID does not belong to an employee."));

        Assignment assignment = new Assignment();
        assignment.setProject(project);
        assignment.setEmployee(employee);

        assignmentRepository.save(assignment);
    }

    public void unassignProject(Long projectId, Long employeeId) {
        Assignment assignment = assignmentRepository.findByProjectProjectIdAndEmployeeUserId(projectId, employeeId)
                .orElse(null);

        if (assignment == null) {
            throw new IllegalArgumentException("Assignment not found.");
        }

        assignmentRepository.delete(assignment);
    }


    public List<Project> getAllProjects() {
        if (projectRepository == null) {
            throw new IllegalStateException("Project not found.");
        }
        return projectRepository.findAll();
    }


    public List<User> getAllEmployees() {
        if (userRepository == null) {
            throw new IllegalStateException("Employee not found.");
        }
        return userRepository.findByRole(User.Role.EMPLOYEE);
    }


    public List<User> getAllManagers() {
        if (userRepository == null) {
            throw new IllegalStateException("Manager not found.");
        }
        return userRepository.findByRole(User.Role.MANAGER);
    }

    public List<User> getAllUsers() {
        if (userRepository == null) {
            throw new IllegalStateException("User not found.");
        }
        return userRepository.findAll();
    }


    public List<ProjectDetails> getAllProjectDetails() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(project -> {
            List<String> employeeNames = project.getProjectAssignments().stream()
                    .map(assignment -> assignment.getEmployee().getFirstname() + " " + assignment.getEmployee().getLastname())
                    .collect(Collectors.toList());
            return new ProjectDetails(project.getProjectId(), project.getName(),
                    project.getManager().getFirstname() + " " + project.getManager().getLastname(),
                    employeeNames);
        }).collect(Collectors.toList());
    }

    public void deleteEmployee(Long employeeId) {
        User employee = userRepository.findByUserIdAndRole(employeeId, User.Role.EMPLOYEE)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));
        userRepository.delete(employee);
    }

    public void updateEmployee(Long employeeId, UserRequest userRequest) {
        User employee = userRepository.findByUserIdAndRole(employeeId, User.Role.EMPLOYEE)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));

        employee.setFirstname(userRequest.getFirstname());
        employee.setLastname(userRequest.getLastname());
        employee.setEmail(userRequest.getEmail());

        userRepository.save(employee);
    }

    public List<RequestResource> getAllResourceRequests() {
        return requestResourceRepository.findAll();
    }
    public RequestResource approveResourceRequest(Long requestId) {
        RequestResource request = requestResourceRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        request.setStatus(RequestStatus.APPROVED);
        return requestResourceRepository.save(request);
    }

    public RequestResource rejectResourceRequest(Long requestId) {
        RequestResource request = requestResourceRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        request.setStatus(RequestStatus.REJECTED);
        return requestResourceRepository.save(request);
    }

}