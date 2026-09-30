package com.models.practiceproject.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressRequestDto {
    @NotBlank(message = "Street is required ")
    private String street;
    @NotBlank(message = "City is required please enter ")
    private String city;
    @NotBlank(message = "State is required pleased enter")
    private String state;
    @NotNull(message = "Zipcode is required")
    @Size(max = 6,message = "Zipcode should not be greater than 6 digit ")
    private Integer zipcode;

    private String country;
}
