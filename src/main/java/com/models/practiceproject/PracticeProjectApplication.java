package com.models.practiceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication // (exclude = { DataSourceAutoConfiguration.class} )
@EnableJpaAuditing (auditorAwareRef = "auditorAwareImpl")
public class PracticeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeProjectApplication.class, args);
        System.out.println("PracticeProjectApplication started successfully");
    }

}
