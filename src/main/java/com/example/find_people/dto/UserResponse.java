package com.example.find_people.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class UserResponse {
    @NotEmpty
    private final String uid;

    @NotEmpty
    private final String email;

    private final String name;

    private final String age;

    private final String gender;
}
