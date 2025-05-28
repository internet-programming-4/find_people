package com.example.find_people.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@RequiredArgsConstructor
public class BoardResponse {
    @NotNull
    private final Long id;

    @NotEmpty
    private final String title;

    @NotEmpty
    private final String content;

    @NotEmpty
    private final String writer;

    private final String status;

    @NotNull
    private final int number;

    @NotNull
    private final LocalDateTime createdAt;
}
