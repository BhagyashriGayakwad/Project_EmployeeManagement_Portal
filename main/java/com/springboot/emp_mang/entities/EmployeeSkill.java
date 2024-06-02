package com.springboot.emp_mang.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employee_skills")
public class EmployeeSkill {
    @EmbeddedId
    private EmployeeSkillId id;

    @Embeddable
    public static class EmployeeSkillId implements Serializable {
        @Column(name = "employee_user_id")
        private Long employeeUserId;

        @Column(name = "skill_id")
        private Long skillId;


        public EmployeeSkillId() {
            super();
            // TODO Auto-generated constructor stub
        }


        public EmployeeSkillId(Long employeeUserId, Long skillId) {
            super();
            this.employeeUserId = employeeUserId;
            this.skillId = skillId;
        }


        public Long getEmployeeUserId() {
            return employeeUserId;
        }


        public void setEmployeeUserId(Long employeeUserId) {
            this.employeeUserId = employeeUserId;
        }


        public Long getSkillId() {
            return skillId;
        }


        public void setSkillId(Long skillId) {
            this.skillId = skillId;
        }



    }

    public EmployeeSkill() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EmployeeSkill(EmployeeSkillId id) {
        super();
        this.id = id;
    }

    public EmployeeSkillId getId() {
        return id;
    }

    public void setId(EmployeeSkillId id) {
        this.id = id;
    }



}