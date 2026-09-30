package com.models.practiceproject.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {

    @NotBlank(message = "course name is required ")
    private String courseName;
    @NotBlank(message = "course duration is required ")
    private String courseDuration;
    @NotNull(message = "please enter course fee ")
    private Double fees;
    @NotBlank(message = "instructor name is required ")
    private String instructorName;
    @NotBlank(message = "email is required ")
    private String instructorEmail;
    @NotBlank(message = "phone number is required ")
    private String instructorPhone;
}
