package com.models.practiceproject.Services;

import com.models.practiceproject.Dto.StudentProjectionDto;
import com.models.practiceproject.Dto.StudentRequestDto;
import com.models.practiceproject.Dto.StudentResponseDto;
import com.models.practiceproject.Entity.Student;
import com.models.practiceproject.Projection.ProjectionResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    StudentResponseDto saveStudent(StudentRequestDto studentRequestDto);
    StudentResponseDto getById(Long id);
    List<StudentResponseDto> getAllStudents();
    StudentResponseDto updateStudent(StudentRequestDto studentRequestDto,Long id);
    StudentResponseDto deleteStudent(Long id);
    StudentResponseDto findByEmail(String email);
    Long countStudentsByCourse(String courseName);
    StudentResponseDto updateStudentPatch(StudentRequestDto studentRequestDto, Long id);
    @Transactional
    void deleteStudentByEmail(String email);
    List<StudentResponseDto> findByFirstNameAndCourseName(String firstName, String courseName);
    List<StudentResponseDto> findByFirstNameContaining(String characters);
    StudentResponseDto findByFirstName(String firstName);

    List<StudentResponseDto> getAllStudetsSortedByFirstname();

    Page<StudentResponseDto> getAllStudentByPage(int page, int size);

    Page<StudentResponseDto> getStudents(Pageable pageable);

    List<ProjectionResponse> getAllStudentsByProjections();

    List<StudentProjectionDto> getAllStudentProjectionsDto();
    public List<StudentResponseDto> searchStudent(String firstName,String email,String courses);
}
