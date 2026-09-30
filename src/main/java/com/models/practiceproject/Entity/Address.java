package com.models.practiceproject.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  street;
    private String city;
    private String state;
    private Integer zipcode;
    private String country;

    @OneToOne(mappedBy = "address")
    private Student student;
}
