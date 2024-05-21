package com.example.employeemanagement.model;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="emp-skills")
public class EmployeeSkill {
    @EmbeddedId
    private EmployeeSkillId id;

    @Embeddable
    public static class EmployeeSkillId implements Serializable {
        @Column(name = "employee_user_id")
        private Long employeeUserId;

        @Column(name = "skill_id")
        private Long skillId;


    }
}
