package com.amich.provider.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


@Data
@Builder
public class Provider {
    private Long id;

    private String name;

    private String address;

    private String email;

    private String phoneNumber;

    @EqualsAndHashCode.Exclude
    private Set<Address> addresses;

    // Default constructor required by JPA
    public Provider() {
    }

    // Optional constructor for convenience
    public Provider(String name, String address, String email, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Provider(Long id, String name, String address, String email, String phoneNumber, Set<Address> addresses) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addresses = addresses;
    }

    // Getters and setters are generated by Lombok
}

