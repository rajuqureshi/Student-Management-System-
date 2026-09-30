package com.models.practiceproject.Repositories;

import com.models.practiceproject.Dto.StudentProjectionDto;
import com.models.practiceproject.Dto.StudentResponseDto;
import com.models.practiceproject.Entity.Student;
import com.models.practiceproject.Projection.ProjectionResponse;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
//    .............Derived queries.......................
    boolean existsByEmail(String email);
    Optional<Student> findByEmail(String email);

//    .............JPQl queries.......................

    @Query(
            """
            SELECT COUNT(s)
            FROM Student s
            JOIN s.courses c
            WHERE LOWER(c.courseName) = LOWER(:courseName)
            """)
    Long countStudentsByCourseName(@Param("courseName") String courseName);
    @Transactional
    void deleteByEmail(String email);
    @Query(
            """
                SELECT s FROM Student s
                JOIN s.courses c
                WHERE s.firstName = :firstName
                AND LOWER(c.courseName) = LOWER(:courseName)
            """)
    public List<Student> findByFirstNameAndCourse(@Param("firstName") String firstName,
                                                  @Param("courseName") String courseName);

    List<Student> findByFirstNameContaining(String characters);
//    .............Native sql queries.......................

    @Query(value = "SELECT * FROM student WHERE first_name=:first_name",
    nativeQuery = true)
    Optional<Student> findByFirstName(@Param("first_name") String firstName);

    List<ProjectionResponse> findBy();



    @Query("""
            SELECT new com.models.practiceproject.Dto.StudentProjectionDto(
                s.firstName,
                s.lastName,
                s.email,
                s.age
            )
            FROM Student s
            """)
    List<StudentProjectionDto> getStudentProjection();

//    N+1 Problem solution using JPQL
    @Query("""
            SELECT DISTINCT s
            FROM Student s
            LEFT JOIN FETCH s.courses
            LEFT JOIN FETCH s.department
            LEFT JOIN FETCH s.address
            """)
    public List<Student> getAllStudentDetails();


//    N+1 Problem solution using @EntityGraph
//    @EntityGraph(attributePaths = {"address,department,courses"})
//    @Query("SELECT s FROM Student s")
//    public List<Student> getAllStudentDetails();
}
