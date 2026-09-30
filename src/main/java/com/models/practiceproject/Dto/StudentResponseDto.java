package com.models.practiceproject.Dto;

import com.models.practiceproject.Entity.Department;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private AddressResponseDto address;
    private String departmentName;
    private String departmentCode;
    private String departmentType;
    private String message;
    private List<CourseResponseDto> courses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

}
