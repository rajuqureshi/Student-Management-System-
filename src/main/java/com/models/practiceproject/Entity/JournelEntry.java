package com.models.practiceproject.Entity;

import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Document
public class JournelEntry {
    @Id
    private String id;
    private String title;
    private int price;
}
