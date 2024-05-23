package com.emp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<com.emp.entities.ProjectAssign, Long> {
    List<com.emp.entities.ProjectAssign> findByEmployeeUserId(Long employeeId);
    Optional<com.emp.entities.ProjectAssign> findByProjectProjectIdAndEmployeeUserId(Long projectId, Long employeeId);
}
