package com.models.practiceproject.Services;

import com.models.practiceproject.Dto.CourseRequestDto;
import com.models.practiceproject.Dto.CourseResponseDto;
import com.models.practiceproject.Entity.Course;
import com.models.practiceproject.Exception.CourseNotFoundException;
import com.models.practiceproject.Repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImp implements CourseService{
    private CourseRepository courseRepository;
    public CourseServiceImp(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    @Override
    public CourseResponseDto CreateCourse(CourseRequestDto courseRequestDto) {
        Course course = courseEntity(courseRequestDto);
        return courseMapper(course);
    }

    @Override
    public List<CourseResponseDto> GetAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::courseMapper)
                .toList();
    }

    @Override
    public CourseResponseDto GetCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new CourseNotFoundException("Course is not Found"));
        return courseMapper(course);
    }

    public Course courseEntity(CourseRequestDto courseRequestDto) {
        Course course = new Course();
        course.setFees(courseRequestDto.getFees());
        course.setCourseName(courseRequestDto.getCourseName());
        course.setCourseDuration(courseRequestDto.getCourseDuration());
        course.setInstructorName(courseRequestDto.getInstructorName());
        course.setInstructorEmail(courseRequestDto.getInstructorEmail());
        course.setInstructorPhone(courseRequestDto.getInstructorPhone());
        Course savedCourse = courseRepository.save(course);
        return savedCourse;
    }
    public CourseResponseDto courseMapper(Course course) {
        CourseResponseDto courseResponseDto = new CourseResponseDto();
        courseResponseDto.setId(course.getId());
        courseResponseDto.setFees(course.getFees());
        courseResponseDto.setCourseName(course.getCourseName());
        courseResponseDto.setCourseDuration(course.getCourseDuration());
        courseResponseDto.setInstructorName(course.getInstructorName());
        courseResponseDto.setInstructorEmail(course.getInstructorEmail());
        courseResponseDto.setInstructorPhone(course.getInstructorPhone());
        return courseResponseDto;
    }
}
