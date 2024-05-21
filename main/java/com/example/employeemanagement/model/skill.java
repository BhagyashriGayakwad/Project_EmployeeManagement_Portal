package com.example.employeemanagement.model;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "skills")
public class skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    private String name;

    @OneToMany(mappedBy = "skill")
    private List<RequestResource> skillRequestResources;

    @ManyToMany(mappedBy = "skills", cascade = CascadeType.ALL)
    private List<User> employees;

    public Skill() {
        super();
        // TODO Auto-generated constructor stub
    }
}
