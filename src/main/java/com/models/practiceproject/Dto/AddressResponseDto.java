package com.models.practiceproject.Dto;

import lombok.Data;

@Data
public class AddressResponseDto {
    private Long id;
    private String street;
    private String city;
    private String state;
    private Integer zipcode;
}
