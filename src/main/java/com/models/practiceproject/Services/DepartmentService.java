package com.models.practiceproject.Services;

import com.models.practiceproject.Dto.DepartmentRequestDto;
import com.models.practiceproject.Dto.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto);
    List<DepartmentResponseDto> findAllDepartments();
    DepartmentResponseDto findDepartmentById(Long id);
    DepartmentResponseDto updateDepartment(DepartmentRequestDto departmentRequestDto,Long id);
    void deleteDepartmentById(Long id);
}
