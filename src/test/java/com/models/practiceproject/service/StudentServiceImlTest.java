package com.models.practiceproject.service;

import com.models.practiceproject.Dto.StudentResponseDto;
import com.models.practiceproject.Entity.Address;
import com.models.practiceproject.Entity.Department;
import com.models.practiceproject.Entity.Student;
import com.models.practiceproject.Exception.StudentNotFoundException;
import com.models.practiceproject.Repositories.StudentRepository;
import com.models.practiceproject.Services.StudentService;
import com.models.practiceproject.Services.StudentServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImlTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImp studentService;

    @Test
    public void shouldReturnStudentById(){
//        Arrange
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("johndoe@gmail.com");
        student.setPassword("12345");
        student.setAge(42);

        Address address = new Address();
        address.setId(1L);
        address.setStreet("35 street");
        address.setCity("Los Anglas");
        address.setState("USA");
        address.setZipcode(546823);
        address.setCountry("America");

        student.setAddress(address);

        Department department = new Department();
        department.setId(1L);
        department.setDepartmentName("CS");
        department.setDepartmentCode("452");
        department.setDepartmentType("private");
        student.setDepartment(department);

        student.setCourses(List.of());
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student)); //use for any long number eneter
//        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

//        Act
        StudentResponseDto responseDto = studentService.getById(1L);

//        Assert
        assertEquals(1L,responseDto.getId());
        assertEquals("John",responseDto.getFirstName());
        assertEquals("Doe",responseDto.getLastName());

        verify(studentRepository,times(1)).findById(1L);
        verify(studentRepository,never()).deleteById(1L);
    }

    @Test
    public void shouldThrowExceptionWhenStudentRespositoryFails(){
//        Arrange
        when(studentRepository.findById(1L))
                .thenThrow(new RuntimeException());

//        studentService.getById(1L);

//        using assert method throw exception

        RuntimeException ex  = assertThrows(
                RuntimeException.class,
                () -> studentService.getById(1L)
        );
    }

    @Test
    public void shouldReturnStudentNotFound(){
//        Arrange
        when(studentRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(
                StudentNotFoundException.class,
                () -> studentService.getById(100L)
        );
    }
}
