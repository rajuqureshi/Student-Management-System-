package com.models.practiceproject.Dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DepartmentRequestDto {
    @NotBlank(message = "Department name is required ")
    @Size(max=150,message = "Department name must be 150 charater")
    private String departmentName;
    @NotNull(message = "Department code is required ")
//    @Min(value = 1,message = "department code must be atleast 1 digit ")
//    @Max(value = 9999,message = "Department code must not be exceed 6 digit")
    @Size(max = 6,message = "department lenght must be 6 character")
    private String departmentCode;
    @NotNull(message = "department type is required : ")
    private String departmentType;
}
