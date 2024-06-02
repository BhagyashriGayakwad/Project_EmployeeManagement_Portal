package com.springboot.emp_mang.repository;

import com.springboot.emp_mang.entities.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, EmployeeSkill> {
    List<EmployeeSkill> findByIdEmployeeUserId(Long employeeId);
}

