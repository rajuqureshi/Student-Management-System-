package com.models.practiceproject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String courseName;
    private String courseDuration;
    private Double fees;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();
}
