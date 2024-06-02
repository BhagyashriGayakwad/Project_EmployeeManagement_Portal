package com.springboot.emp_mang.service;

import com.springboot.emp_mang.dto.UserRequest;
import com.springboot.emp_mang.entities.*;
import com.springboot.emp_mang.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    private  ProjectRepository projectRepository;

    @Autowired
    private RequestResourceRepository requestResourceRepository;

    @Autowired
    private com.springboot.emp_mang.dto.RequestResource requestResource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean updateUserProfile(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        if (!user.getEmail().equals(userRequest.getEmail()) && userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        if (userRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        userRepository.save(user);
        return true;
    }

    public List<User> filterEmployeesBySkills(List<String> skillNames) {
        Set<Skill> skills = new HashSet<>();
        for (String skillName : skillNames) {
            Skill skill = skillRepository.findByNameIgnoreCase(skillName)
                    .orElseThrow(() -> new IllegalArgumentException("Skill not found: " + skillName));
            skills.add(skill);
        }

        List<Long> employeeIds = employeeSkillRepository.findAll().stream()
                .filter(es -> skills.contains(skillRepository.findById(es.getId().getSkillId()).orElse(null)))
                .map(EmployeeSkill::getId)
                .map(EmployeeSkill.EmployeeSkillId::getEmployeeUserId)
                .distinct()
                .collect(Collectors.toList());

        return userRepository.findByRoleAndUserIdIn(User.Role.EMPLOYEE, employeeIds);
    }

    public List<User> getUnassignedEmployees() {
        List<Long> assignedEmployeeIds = assignmentRepository.findAll().stream()
                .map(Assignment::getEmployee)
                .map(User::getUserId)
                .collect(Collectors.toList());

        return userRepository.findByRoleAndUserIdNotIn(User.Role.EMPLOYEE, assignedEmployeeIds);
    }

    public List<com.springboot.emp_mang.entities.RequestResource> createRequestResources(com.springboot.emp_mang.dto.RequestResource requestResourceRequest) {
        Project project = projectRepository.findById(requestResourceRequest.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        User manager = userRepository.findById(requestResourceRequest.getManagerId())
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        List<User> employees = new ArrayList<>();
        for (String employeeEmail : requestResourceRequest.getEmployeeEmails()) {
            Optional<User> optionalUser = userRepository.findByEmail(employeeEmail.trim());
            if (optionalUser.isPresent() && optionalUser.get().getRole() == User.Role.EMPLOYEE) {
                employees.add(optionalUser.get());
            }
        }

        if (employees.isEmpty()) {
            throw new IllegalArgumentException("No valid employees found");
        }

        List<com.springboot.emp_mang.entities.RequestResource> requestResources = new ArrayList<>();
        for (User employee : employees) {
            com.springboot.emp_mang.entities.RequestResource requestResource = new com.springboot.emp_mang.entities.RequestResource();
            requestResource.setManager(manager);
            requestResource.setProject(project);
            requestResource.setEmployee(employee);
            requestResource.setStatus(com.springboot.emp_mang.entities.RequestResource.RequestStatus.PENDING);
            requestResources.add(requestResourceRepository.save(requestResource));
        }

        return requestResources;
    }
}
