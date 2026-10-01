package com.models.practiceproject.Controller;

import com.models.practiceproject.Dto.StudentProjectionDto;
import com.models.practiceproject.Dto.StudentRequestDto;
import com.models.practiceproject.Dto.StudentResponseDto;
import com.models.practiceproject.Entity.Student;
import com.models.practiceproject.Projection.ProjectionResponse;
import com.models.practiceproject.Services.StudentService;
import com.models.practiceproject.payload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/students")
@Tag(
        name = "Student Apis",
        description = "Apis managing for all students"
)
public class StudentController {
    private StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
            summary = "Create a Students",
            description = "Creating a new Student"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDto>> saveStudent(@Valid @RequestBody StudentRequestDto studentDto) {
        StudentResponseDto studentResponseDto =  studentService.saveStudent(studentDto);
        ApiResponse<StudentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Student created successfully",
                studentResponseDto
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }
    @Operation(
            summary = "Get student by id",
            description = "this is a api endpoint for get student by id "
    )

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> getStudentById(@Parameter(description = "Student id")
                                                                              @PathVariable Long id) {
        StudentResponseDto student = studentService.getById(id);
        if (student==null){
            return ResponseEntity.notFound().build();
        }
        ApiResponse<StudentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student fetched successfullu",
                student
        );
        return ResponseEntity.ok(apiResponse);
    }
    @Operation(
            summary = "Get All Students",
            description = "this is a api endpoint for get all students"
    )

    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "This is api response code and description"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "Student not found"
            )
    })

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> getAllStudents( ) {
        List<StudentResponseDto> studentResponseDtos = studentService.getAllStudents();
        ApiResponse<List<StudentResponseDto>> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Students fetched Successfully",
                studentResponseDtos
        );
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Update a Students",
            description = "update existing student"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudent(@Valid @RequestBody StudentRequestDto studentRequestDto, @PathVariable Long id){
      StudentResponseDto student  =studentService.updateStudent(studentRequestDto,id);
        ApiResponse<StudentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Student updated successfully",
                student
        );
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "update Student by emil",
            description = "updating students by email"
    )
    @GetMapping("/email")
    public ResponseEntity<ApiResponse<StudentResponseDto>> findByEmail(@RequestParam String email) {
        StudentResponseDto studentResponseDto = studentService.findByEmail(email);
        ApiResponse<StudentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Studetns Fetch succssfully",
                studentResponseDto
        );
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student deleted successfully",
                null
        );
        return ResponseEntity.ok(apiResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudentPatch(@RequestBody StudentRequestDto studentRequestDto, @PathVariable Long id) {
        StudentResponseDto studentResponseDto = studentService.updateStudentPatch(studentRequestDto,id);
        ApiResponse<StudentResponseDto> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student updated patchly successfully",
                studentResponseDto
        );
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Count All Students by courseName",
            description = "counting all students by courseName"
    )
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> countStudentsByCourse(@RequestParam String courseName) {
        Long count  = studentService.countStudentsByCourse(courseName);
        ApiResponse<Long> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Students fetch successfully",
                count
        );
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/email")
    public ResponseEntity<ApiResponse<String>> deleteStudent(@RequestParam String email) {
        studentService.deleteStudentByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Students deletaed successfully",
                null
        ));

    }
    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDto>> findByFirstNameAndCourse(@RequestParam String firstName,
                                                                             @RequestParam String courseName){
        return ResponseEntity.ok(
                studentService.findByFirstNameAndCourseName(firstName, courseName)
        );
    }

    @GetMapping("/search/char")
    public ResponseEntity<List<StudentResponseDto>> findByFirstNameContaining(@RequestParam String firstName){
        return ResponseEntity.ok(
                studentService.findByFirstNameContaining(firstName)
        );
    }
    @GetMapping("/nativequery")
    public ResponseEntity<StudentResponseDto> findByStudentFirstName(@RequestParam String firstName){
        return ResponseEntity.ok(studentService.findByFirstName(firstName));
    }

    @GetMapping("/sorting")
    public ResponseEntity<List<StudentResponseDto>> getSortedStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<StudentResponseDto>> findAllStudents(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5")int size){
        return ResponseEntity.ok(studentService.getAllStudentByPage(page,size));
    }

//    Dynamic Sorting using Pageable interface

    @GetMapping("/dynamicSorting")
    public ResponseEntity<Page<StudentResponseDto>> getStudents(Pageable pageable){
        return ResponseEntity.ok(studentService.getStudents(pageable));
    }

//    Find studetns by projection or columns
    @GetMapping("/projection")
    public ResponseEntity<List<ProjectionResponse>> getAllStudentsByProjections(){
        return ResponseEntity.ok(studentService.getAllStudentsByProjections());
    }

    @GetMapping("/projectiondto")
    public  ResponseEntity<List<StudentProjectionDto>> getAllStudentProjectionsDto(){
        return ResponseEntity.ok(studentService.getAllStudentProjectionsDto());
    }

//    using Specification class and filtering data by entering multiple field at a time

    @GetMapping("/search/specification")
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> searchStudent(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String courses
            ){
        List<StudentResponseDto> students = studentService.searchStudent(firstName,email,courses);
        ApiResponse<List<StudentResponseDto>> apiResponse = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student fetched successfully",
                students
        );
        return ResponseEntity.ok(apiResponse);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudentResponseDto> uploadProfileImage(
            @PathVariable Long id, @RequestParam("file") MultipartFile profileImage){
        return ResponseEntity.ok(studentService.uploadProfileImage(id, profileImage));
    }
}
