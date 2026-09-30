package com.models.practiceproject.specification;

import com.models.practiceproject.Entity.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {
    public static Specification<Student> byFirstName(String firstName) {
        if(firstName == null || firstName.isBlank()) {
            return Specification.unrestricted();
        }
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("firstName"),firstName);
//                        "%" + firstName.toLowerCase() + "%"
//                );
    }

    public static Specification<Student> byEmail(String email) {
        if(email == null || email.isBlank()) {
            return Specification.unrestricted();
        }
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<Student> byCourses(String courses) {
        if(courses == null || courses.isBlank()) {
            return Specification.unrestricted();
        }
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("courses").get("courseName"),courses);
//                        "%" + courses.toLowerCase() + "%");
    }
}
