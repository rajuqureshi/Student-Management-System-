package com.models.practiceproject.Controller;

import com.models.practiceproject.Dto.CourseRequestDto;
import com.models.practiceproject.Dto.CourseResponseDto;
import com.models.practiceproject.Services.CourseService;
import com.models.practiceproject.payload.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CourseController {
    private CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponseDto>> createCourse(@Valid @RequestBody CourseRequestDto courseRequestDto) {
        CourseResponseDto courseResponseDto = courseService.CreateCourse(courseRequestDto);
        ApiResponse<CourseResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Course has been save",
                courseResponseDto
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponseDto>>> getAllCourses() {
        List<CourseResponseDto> courseResponseDto = courseService.GetAllCourses();
        ApiResponse<List<CourseResponseDto>> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "See all course here ",
                courseResponseDto
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDto>> getCourseById(@PathVariable Long id) {
        CourseResponseDto courseResponseDto = courseService.GetCourseById(id);
        ApiResponse<CourseResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "See One Course Details",
                courseResponseDto
        );
        return ResponseEntity.ok(apiResponse);
    }
}
