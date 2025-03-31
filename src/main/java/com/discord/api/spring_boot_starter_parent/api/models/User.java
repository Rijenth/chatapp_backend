package com.discord.api.spring_boot_starter_parent.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

//    @Column(nullable = false)
//    private String lastName;
//
//    @Column(nullable = false)
//    private String firstName;

    @Column(nullable = false)
    private String password;

    private String role;

}