package com.models.practiceproject.Services;

import com.models.practiceproject.Dto.*;
import com.models.practiceproject.Entity.Address;
import com.models.practiceproject.Entity.Course;
import com.models.practiceproject.Entity.Department;
import com.models.practiceproject.Entity.Student;
import com.models.practiceproject.Exception.DuplicateEmailException;
import com.models.practiceproject.Exception.ResourceNotFoundException;
import com.models.practiceproject.Exception.StudentNotFoundException;
import com.models.practiceproject.Projection.ProjectionResponse;
import com.models.practiceproject.Repositories.CourseRepository;
import com.models.practiceproject.Repositories.DepartmentRepository;
import com.models.practiceproject.Repositories.StudentRepository;
import com.models.practiceproject.specification.StudentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentServiceImp implements StudentService {
    private StudentRepository studentRepository;
    private DepartmentRepository departmentRepository;
    private CourseRepository courseRepository;
    private PasswordEncoder passwordEncoder;
    private FileStorageService fileStorageService;
    public StudentServiceImp(StudentRepository studentRepository,
                             DepartmentRepository departmentRepository,
                             CourseRepository courseRepository,
                             PasswordEncoder passwordEncoder,
                             FileStorageService fileStorageService) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder =  passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    @Override
    public StudentResponseDto saveStudent(StudentRequestDto studentDto) {
        if (studentRepository.existsByEmail(studentDto.getEmail())) {
            throw new DuplicateEmailException("Email already exists" + studentDto.getEmail());
        }

        Student student1 = mapToEntity(studentDto);
        Student studentSave = studentRepository.save(student1);
        return mapToDto(studentSave);
    }

    @Override
    public StudentResponseDto getById(Long id) {
        log.debug("Fetching student with {} id ",id);
        Student studentResponseDto =  studentRepository.findById(id)
                .orElseThrow(()-> {
                    log.warn("Student not found with {} id ",id);
                   return new StudentNotFoundException("student not found with id: " + id);
                });
        log.info("Student found with {} id",id);
        return mapToDto(studentResponseDto);
    }

    @Override
    public StudentResponseDto findByEmail(String email) {
        Optional<Student> studentOptional = studentRepository.findByEmail(email);
        if (studentOptional.isPresent()) {
            return mapToDto(studentOptional.get());
        }
        throw new StudentNotFoundException("student not found with email: " + email);
    }

    @Override
    public Long countStudentsByCourse(String courseName) {
        Long count  = studentRepository.countStudentsByCourseName(courseName);
        return count;
    }

    @Override
    public List<StudentResponseDto> getAllStudents( ) {
        log.info("=================Fetching all students=========================");
        List<StudentResponseDto> students = studentRepository.getAllStudentDetails()
                .stream()
                .map(this::mapToDto)
                .toList();
        log.info("All students are fetched succssfully {} students " + students.size());
        return students;
    }

    @Override
    public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto,Long id) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(()->  new StudentNotFoundException("Student not found with id " + id));

        if (studentRepository.existsByEmail(studentRequestDto.getEmail())
                && !existingStudent.getEmail().equals(studentRequestDto.getEmail())) {
            throw new DuplicateEmailException("student already exists with email: " + studentRequestDto.getEmail());
        }

        Address address = existingStudent.getAddress();
        if (address == null) {
            address = new Address();
        }
        Department department = existingStudent.getDepartment();
        if (department == null) {
            Department department1 = new Department();
        }
        Student studentUpdate = mapToEntity(studentRequestDto);
        Student updatedStudent =studentRepository.save(existingStudent);

        return mapToDto(updatedStudent);
    }

    @Override
    public StudentResponseDto deleteStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional!=null) {
            studentOptional.get().setDeleted(true);
            studentRepository.save(studentOptional.get());
        } else {
            throw new StudentNotFoundException("Student not found with id " + id);
        }
        return null;
    }

    @Override
    public StudentResponseDto updateStudentPatch(StudentRequestDto studentRequestDto, Long id) {
        Student existngStudent = studentRepository.findById(id)
                .orElseThrow(()-> new StudentNotFoundException("Student not found with id " + id));

            if (studentRequestDto.getFirstName() != null) {
                existngStudent.setFirstName(studentRequestDto.getFirstName());
            }
            if (studentRequestDto.getLastName() != null) {
                existngStudent.setLastName(studentRequestDto.getLastName());
            }
            if (studentRequestDto.getEmail() != null) {
                if (studentRepository.existsByEmail(studentRequestDto.getEmail())
                        && !existngStudent.getEmail().equals(studentRequestDto.getEmail())) {
                    throw new DuplicateEmailException("Student with email " + studentRequestDto.getEmail() + " already exists");
                }
                existngStudent.setEmail(studentRequestDto.getEmail());
            }
            if (studentRequestDto.getAge() > 0) {
                existngStudent.setAge(studentRequestDto.getAge());
            }

            if (studentRequestDto.getAddress() != null) {
                Address address = existngStudent.getAddress();
                if (address.getStreet() != null) {
                    address.setStreet(studentRequestDto.getAddress().getStreet());
                }
                if (address.getCity() != null) {
                    address.setCity(studentRequestDto.getAddress().getCity());
                }
                if (address.getState() != null) {
                    address.setState(studentRequestDto.getAddress().getState());
                }
                if (address.getZipcode()!=null){
                    address.setZipcode(studentRequestDto.getAddress().getZipcode());
                }
                if (address.getCountry() != null) {
                    address.setCountry(studentRequestDto.getAddress().getCountry());
                }
                existngStudent.setAddress(address);
            }
            Department department = departmentRepository.findById(id)
                    .orElseThrow(()->
                            new ResourceNotFoundException("Department not found with id " + id)
                    );
            existngStudent.setDepartment(department);
           Student updatedStudent =studentRepository.save(existngStudent);

        return mapToDto(updatedStudent);
    }

    @Override
    public void deleteStudentByEmail(String email) {
        Optional<Student> studentOptional = studentRepository.findByEmail(email);
        if (studentOptional.isPresent()) {
            studentOptional.get().setDeleted(true);
            studentRepository.save(studentOptional.get());
        }
        else {
            throw new StudentNotFoundException("Student not found with email " + email);
        }
    }

    @Override
    public List<StudentResponseDto> findByFirstNameAndCourseName(String firstName, String courseName) {
        return studentRepository.findByFirstNameAndCourse(firstName,courseName)
                .stream()
                .map(this:: mapToDto)
                .toList();
    }

    @Override
    public List<StudentResponseDto> findByFirstNameContaining(String characters) {
        return studentRepository.findByFirstNameContaining(characters)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public StudentResponseDto findByFirstName(String firstName) {
        Student student = studentRepository.findByFirstName(firstName)
                .orElseThrow(()-> new StudentNotFoundException("Student not found with name " + firstName));
        return mapToDto(student);
    }

    @Override
    public List<StudentResponseDto> getAllStudetsSortedByFirstname() {
        List<Student> students = studentRepository.findAll(
//                Sort.by("id").descending()
//                Alternative method
                Sort.by(
                        Sort.Direction.DESC,
                        "lastName"
                )
        );
        return students.stream().map(this::mapToDto).toList();
    }

    @Override
    public Page<StudentResponseDto> getAllStudentByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.ASC, "firstName"));
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(this::mapToDto);
    }


//    Dyanamic Sorting using pageable interface
    public Page<StudentResponseDto> getStudents(Pageable pageable){
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(this::mapToDto);
    }

    @Override
    public List<ProjectionResponse> getAllStudentsByProjections() {
        return studentRepository.findBy();
    }

    @Override
    public List<StudentProjectionDto> getAllStudentProjectionsDto() {
        return studentRepository.getStudentProjection();
    }



//    using Specification class and filtering data by entering multiple field at a time
    @Override
    public List<StudentResponseDto> searchStudent(String firstName, String email,String courses) {
        Specification<Student> specification = Specification.allOf(
                StudentSpecification.byFirstName(firstName),
                StudentSpecification.byEmail(email),
                StudentSpecification.byCourses(courses));
        List<Student> students = studentRepository.findAll(specification);
        return students.stream().map(this::mapToDto).toList();
    }

//    File upload service method
    @Transactional
    @Override
    public StudentResponseDto uploadProfileImage(Long id, MultipartFile file) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()->
                        new StudentNotFoundException("Student not found with id " + id));
//        Validation performing
        if (file.isEmpty()) {
            throw new IllegalStateException("File should not be  empty");
        }

        if (!"image/jpeg".equals(file.getContentType()) && !"image/png".equals(file.getContentType())) {
            throw new IllegalStateException("Only JPG and PNG file allow to upload ");
        }

        if (file.getSize()>5*1024*1024){
            throw new IllegalArgumentException("File size must be less than 5MB");
        }
        try {
            String fileName = fileStorageService.storeFile(file);
            student.setFileName(fileName);
            Student updatedStudent = studentRepository.save(student);
            return mapToDto(updatedStudent);
        } catch (Exception e){
            throw new RuntimeException("file failed to upload");
        }
    }

    private Student mapToEntity(StudentRequestDto studentReqDto) {
        Address address = new Address();
        address.setStreet(studentReqDto.getAddress().getStreet());
        address.setCity(studentReqDto.getAddress().getCity());
        address.setState(studentReqDto.getAddress().getState());
        address.setZipcode(studentReqDto.getAddress().getZipcode());
        address.setCountry(studentReqDto.getAddress().getCountry());

        Department department = departmentRepository.findById(studentReqDto.getDepartmentId())
                .orElseThrow(()->
                        new ResourceNotFoundException(
                                "Department is not found with id: " + studentReqDto.getDepartmentId())
                );
        List<Course> course = courseRepository.findAllById(studentReqDto.getCourseId());
        Student student = new Student();
        student.setFirstName(studentReqDto.getFirstName());
        student.setLastName(studentReqDto.getLastName());
        student.setEmail(studentReqDto.getEmail());
        student.setAge(studentReqDto.getAge());
        student.setPassword(passwordEncoder.encode(studentReqDto.getPassword()));
        student.setAddress(address);
        if (student.getDepartment() != null) {
            student.setDepartment(department);
        }
        student.setCourses(course);
//        student.setPassword(studentReqDto.getPassword());
        return student;
    }

    public StudentResponseDto mapToDto(Student student) {
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setFirstName(student.getFirstName());
        studentResponseDto.setLastName(student.getLastName());
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setFileName(student.getFileName());
        studentResponseDto.setAge(student.getAge());
        studentResponseDto.setCreatedAt(student.getCreatedAt());
        studentResponseDto.setUpdatedAt(student.getUpdatedAt());
        studentResponseDto.setCreatedBy(student.getCreatedBy());
        studentResponseDto.setUpdatedBy(student.getUpdatedBy());

        AddressResponseDto addressResponseDto = new AddressResponseDto();
        addressResponseDto.setId(student.getAddress().getId());
        addressResponseDto.setStreet(student.getAddress().getStreet());
        addressResponseDto.setCity(student.getAddress().getCity());
        addressResponseDto.setState(student.getAddress().getState());
        addressResponseDto.setZipcode(student.getAddress().getZipcode());
        studentResponseDto.setAddress(addressResponseDto);

        if (student.getDepartment() != null) {
            studentResponseDto.setDepartmentName(student.getDepartment().getDepartmentName());
            studentResponseDto.setDepartmentCode(student.getDepartment().getDepartmentCode());
            studentResponseDto.setDepartmentType(student.getDepartment().getDepartmentType());
        }else {
            studentResponseDto.setDepartmentName("null");
            studentResponseDto.setDepartmentCode("null");
            studentResponseDto.setDepartmentType("null");
        }

        studentResponseDto.setCourses(
                student.getCourses()
                        .stream()
                        .map(course->
                                new CourseResponseDto(
                                        course.getId(),
                                        course.getCourseName(),
                                        course.getCourseDuration(),
                                        course.getFees(),
                                        course.getInstructorName(),
                                        course.getInstructorEmail(),
                                        course.getInstructorPhone()
                                )).toList()
        );
        studentResponseDto.setMessage("Your response has been ready");
        return studentResponseDto;
    }

}
