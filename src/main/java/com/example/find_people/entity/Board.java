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
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @NotNull
    private String state; // 게시글 상태

    @NotNull
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 작성일시

    @NotNull
    @Column(name = "end_date")
    private LocalDateTime endDate; // 마감일시

    @NotNull
    private int number; // 모집인원 수
}
