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
public class BoardRequest {
    @NotEmpty
    private final String title;

    @NotEmpty
    private final String content;

    @NotNull
    private final LocalDateTime endDate;

    @NotNull
    private final int number;

    @NotNull
    private final Long categoryId;
}
