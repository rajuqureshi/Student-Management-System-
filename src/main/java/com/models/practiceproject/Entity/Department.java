package com.models.practiceproject.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    private String departmentName;
    private String departmentCode;
    private String departmentType;

    @OneToMany(mappedBy = "department")
    private List<Student> students;
}
