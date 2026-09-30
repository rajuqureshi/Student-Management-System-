package com.models.practiceproject.service;

import com.models.practiceproject.Dto.StudentResponseDto;
import com.models.practiceproject.Entity.Address;
import com.models.practiceproject.Entity.Course;
import com.models.practiceproject.Entity.Department;
import com.models.practiceproject.Entity.Student;
import com.models.practiceproject.Exception.StudentNotFoundException;
import com.models.practiceproject.Repositories.CourseRepository;
import com.models.practiceproject.Repositories.DepartmentRepository;
import com.models.practiceproject.Repositories.StudentRepository;
import com.models.practiceproject.Services.StudentService;
import com.models.practiceproject.Services.StudentServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudentServiceImlIntegreationTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentService studentService;
//  Arrange

    @Test
    public void shouldReturnStudentById(){
        Department department = new Department();
        department.setDepartmentName("Computer Science");
        department.setDepartmentCode("1452");
        department.setDepartmentType("Computer");
        departmentRepository.save(department);

        Course course = new Course();
        course.setCourseName("Computer Science");
        course.setCourseDuration("6 Month");
        course.setFees(6500.00);
        course.setInstructorName("Pramod Kumar");
        course.setInstructorPhone("4455661123");
        course.setInstructorEmail("pramodkumar@gmail.com");
        courseRepository.save(course);

        Address address = new Address();
        address.setStreet("34 Street ");
        address.setCity("New York");
        address.setState("NY");
        address.setCountry("USA");
        address.setZipcode(456823);
        Student student = new Student();
        student.setFirstName("Vijay");
        student.setLastName("Kumar");
        student.setEmail("vijaymudgal@gmail.com");
        student.setPassword("Vijay@142");
        student.setAge(23);
        student.setCourses(List.of(course));
        student.setDepartment(department);
        student.setAddress(address);
        Student saveStudent =studentRepository.save(student);

//        Act
        StudentResponseDto studentResponseDto = studentService.getById(saveStudent.getId());

//        Assert
//        assertEquals(saveStudent.getId(),studentResponseDto.getId());
        assertEquals("Vijay", studentResponseDto.getFirstName());
        assertEquals("Kumar", studentResponseDto.getLastName());
        assertEquals(23, studentResponseDto.getAge());
        assertEquals("New York", studentResponseDto.getAddress().getCity());
        assertEquals("NY", studentResponseDto.getAddress().getState());
    }

    @Test
    public void shouldReturnStudentNotFoundException(){
        assertThrows(
                StudentNotFoundException.class,()->
            studentService.getById(999999L));
    }
}
