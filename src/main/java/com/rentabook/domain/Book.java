package com.rentabook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
    private String cover;
    private String genre;
    private Long sold = 0L;
    private String description;
    private String numberOfPage;
}
