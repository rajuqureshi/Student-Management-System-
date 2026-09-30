package com.models.practiceproject.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProjectionDto {
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
