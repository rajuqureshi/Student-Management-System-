package com.models.practiceproject.Services;

import com.models.practiceproject.Dto.DepartmentRequestDto;
import com.models.practiceproject.Dto.DepartmentResponseDto;
import com.models.practiceproject.Dto.StudentResponseDto;
import com.models.practiceproject.Entity.Department;
import com.models.practiceproject.Exception.ResourceNotFoundException;
import com.models.practiceproject.Repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class DepartmentServiceImp implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    public DepartmentServiceImp(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    @Override
    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto) {
        Department department = new Department();
        department.setDepartmentName(departmentRequestDto.getDepartmentName());
        department.setDepartmentCode(departmentRequestDto.getDepartmentCode());
        department.setDepartmentType(departmentRequestDto.getDepartmentType());
        Department saveDepartment = departmentRepository.save(department);
        return deptRespDtoMapper(saveDepartment);
    }

    @Override
    public List<DepartmentResponseDto> findAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::deptRespDtoMapper)
                .toList();
    }

    @Override
    public DepartmentResponseDto findDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Department is not found with id : " +id));
        return deptRespDtoMapper(department);
    }

    @Override
    public DepartmentResponseDto updateDepartment(DepartmentRequestDto departmentRequestDto, Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Department is not found with id : " + id));
        if (department!=null){
            department.setDepartmentName(departmentRequestDto.getDepartmentName());
            department.setDepartmentCode(departmentRequestDto.getDepartmentCode());
            department.setDepartmentType(departmentRequestDto.getDepartmentType());
        }
        return deptRespDtoMapper(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartmentById(Long id) {
        boolean department = departmentRepository.existsById(id);
        if (department) {
            departmentRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Department is not found with id : " + id);
        }
    }

    public DepartmentResponseDto deptRespDtoMapper(Department department) {
        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
        departmentResponseDto.setId(department.getId());
        departmentResponseDto.setDepartmentName(department.getDepartmentName());
        departmentResponseDto.setDepartmentCode(department.getDepartmentCode());
        departmentResponseDto.setDepartmentType(department.getDepartmentType());
        List<StudentResponseDto> studentResponseDtoList = department.getStudents()
                .stream()
                .map(student -> {
                    StudentResponseDto studentResponseDto = new StudentResponseDto();
                    studentResponseDto.setId(student.getId());
                    studentResponseDto.setFirstName(student.getFirstName());
                    studentResponseDto.setLastName(student.getLastName());
                    studentResponseDto.setEmail(student.getEmail());
                    studentResponseDto.setAge(student.getAge());
                    studentResponseDto.setMessage("Success");
                    return studentResponseDto;
                }).toList();
        departmentResponseDto.setStudents(studentResponseDtoList);
        return departmentResponseDto;
    }
}
