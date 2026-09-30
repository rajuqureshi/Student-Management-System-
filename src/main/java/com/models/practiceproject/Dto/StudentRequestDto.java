package com.models.practiceproject.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDto {
    @Column(nullable = false)
    @NotBlank(message = "First name is required")
    @Size(min = 4,max =30,message = "First name must be between 2 and 30 character")
    private String firstName;
    private String lastName;
//    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    @Size(min=10,max=100)
    private String email;
    @Min(value = 1, message = "Your age should be greater than 0 :")
//    @Max(value = 500, message = "Warehouse limit is 500 items")
    private Integer age;

//    Password validation and character required
    @NotBlank(message = "password should not be blank ")
//    @Pattern(
//            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
//            message = "password must contain 8 character and one UpperCase character"
//    )
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must be at least 8 characters and contain uppercase, lowercase, number, and special character"
    )
    private String password;
    @NotNull(message = "Address is required ")
    private AddressRequestDto address;
    @NotNull(message = "Department Id is required ")
    private Long departmentId;
    private List<Long> courseId;
}
