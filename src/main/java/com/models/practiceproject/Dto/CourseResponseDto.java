package com.models.practiceproject.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    private Long id;
    private String courseName;
    private String courseDuration;
    private Double fees;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;

}
