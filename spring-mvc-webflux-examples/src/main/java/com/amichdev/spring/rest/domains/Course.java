package com.amichdev.spring.rest.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Course {

    Long id;
    String name;
    List<Author> authors;
    @Builder.Default
    LocalDateTime localDateTime = LocalDateTime.now();
}
