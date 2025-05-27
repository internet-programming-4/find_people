package com.example.find_people.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String state; // 회원 상태

    @NotNull
    private String name;

    private int age;

    private String gender;

    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;
}
