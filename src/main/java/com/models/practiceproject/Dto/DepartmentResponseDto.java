package com.models.practiceproject.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDto {
    private Long id;
    private String departmentName;
    private String departmentCode;
    private String departmentType;
    private List<StudentResponseDto> students;
}
