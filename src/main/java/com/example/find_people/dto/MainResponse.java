package com.example.find_people.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class MainResponse {
    @NotNull
    private final Long categoryId;

    @NotEmpty
    private final String categoryName;

    @NotNull
    private final List<BoardResponse> boardResponseList;
}
