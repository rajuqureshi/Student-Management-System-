package com.models.practiceproject.Controller;

import com.models.practiceproject.Dto.DepartmentRequestDto;
import com.models.practiceproject.Dto.DepartmentResponseDto;
import com.models.practiceproject.Services.DepartmentService;
import com.models.practiceproject.payload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/department")
public class DepartmentController {
    private DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> createDepartment(
            @Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
        DepartmentResponseDto departmentResponseDto = departmentService.createDepartment(departmentRequestDto);
        ApiResponse<DepartmentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Department fetched succesfully",
                departmentResponseDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> getAllDepartments() {
        List<DepartmentResponseDto> departmentResponseDtos = departmentService.findAllDepartments();
        ApiResponse<List<DepartmentResponseDto>> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "All departments are fetched",
                departmentResponseDtos
        );
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> getDepartmentById(@PathVariable Long id) {
        DepartmentResponseDto departmentResponseDto = departmentService.findDepartmentById(id);
        ApiResponse<DepartmentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Department fetch succssfully by id",
                departmentResponseDto
        );
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponseDto>> updateDepartment(
            @Valid @RequestBody DepartmentRequestDto departmentRequestDto,@PathVariable Long id) {
        DepartmentResponseDto departmentResponseDto = departmentService.updateDepartment(departmentRequestDto, id);
        ApiResponse<DepartmentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Department fetched succesfully",
                departmentResponseDto
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
        return  ResponseEntity.ok("Department deleted successfully");
    }
}

