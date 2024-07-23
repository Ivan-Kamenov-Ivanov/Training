package com.springbootproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "is required")
    private String title;

    @Size(max = 100, message = "Description is too long(max. 100 characters)")
    private String description;

    @FutureOrPresent(message = "cannot be a past date")
    private LocalDate dueDate;

    private boolean isStatusActive;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
