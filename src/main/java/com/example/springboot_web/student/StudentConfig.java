package com.example.springboot_web.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository){
        return args -> {
            Student homer = new Student(
                    "Homer",
                    "homersimpson@yahoo.com",
                    LocalDate.of(1970, Month.APRIL, 6)
            );

            Student marge = new Student(
                    "Marge",
                    "margesimpson@yahoo.com",
                    LocalDate.of(1975, Month.SEPTEMBER, 17)
            );

            Student bart = new Student(
                    "Bart",
                    "elbarto@gmail.com",
                    LocalDate.of(2010, Month.MAY, 5)
            );

            repository.saveAll(
                    List.of(homer, marge, bart));
        };
    }
}
