package com.example.find_people.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @ManyToOne
    @JoinColumn(name = "writer")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @NotEmpty
    private String status; // 게시글 상태

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt; // 작성일시

    @NotNull
    @Column(name = "end_date")
    private LocalDateTime endDate; // 마감일시

    @NotNull
    private int number; // 모집인원 수
}
