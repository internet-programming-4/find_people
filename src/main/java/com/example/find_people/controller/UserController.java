package com.example.find_people.controller;

import com.example.find_people.dto.UserResponse;
import com.example.find_people.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Tag(name = "회원 API")
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "로그인/회원가입 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인/회원가입",
                            content = @Content(
                                    schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "로그인/회원가입 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PostMapping("/save")
    public ResponseEntity<UserResponse> login(@RequestParam String idToken) {
        try {
            return ResponseEntity.ok(userService.login(idToken));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }
}
