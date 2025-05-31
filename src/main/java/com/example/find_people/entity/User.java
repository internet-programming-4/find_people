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
    private String uid; // firebase uid

    @NotNull
    private String status; // 회원 상태

    @NotNull
    private String name;

    private String email;

    private int age;

    private String gender;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
