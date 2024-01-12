package com.amichdev.spring.rest.domains;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Author {

    Long id;
    UUID writerId;
    String name;
}
