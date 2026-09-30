package com.models.practiceproject.Services;

import com.models.practiceproject.Dto.CourseRequestDto;
import com.models.practiceproject.Dto.CourseResponseDto;

import java.util.List;

public interface CourseService {
    CourseResponseDto CreateCourse(CourseRequestDto courseRequestDto);
    List<CourseResponseDto> GetAllCourses();
    CourseResponseDto GetCourseById(Long id);
}
