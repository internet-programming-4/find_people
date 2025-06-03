package com.example.find_people.controller;

import com.example.find_people.dto.BoardResponse;
import com.example.find_people.dto.UserResponse;
import com.example.find_people.entity.Category;
import com.example.find_people.service.AdminService;
import com.google.firebase.auth.FirebaseAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final AdminService adminService;
    private final FirebaseAuth firebaseAuth;

    @Operation(
            summary = "카테고리 생성 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "생성된 카테고리",
                            content = @Content(
                                    schema = @Schema(implementation = Category.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "카테고리 생성 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/category")
    public Category addCategory(@RequestParam String name) {
        return adminService.addCategory(name);
    }

    @Operation(
            summary = "카테고리 수정 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "수정된 카테고리",
                            content = @Content(
                                    schema = @Schema(implementation = Category.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "카테고리 수정 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/category/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestParam String name) {
        return adminService.updateCategory(id, name);
    }

    @Operation(
            summary = "카테고리 삭제 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카테고리 삭제 성공"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "카테고리 삭제 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
    }

    @Operation(
            summary = "카테고리 목록 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "카테고리 목록 조회 성공",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = Category.class)))

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "카테고리 목록 조회 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return adminService.getAllCategories();
    }

    @Operation(
            summary = "게시글 비활성화 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "비활성화한 게시글",
                            content = @Content(
                                    schema = @Schema(implementation = BoardResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "게시글 비활성화 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/board/{id}/deactivate")
    public BoardResponse deactivateBoard(@PathVariable Long id) {
        return adminService.deactivateBoard(id);
    }

    @Operation(
            summary = "권한 부여 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "권한 부여 성공",
                            content = @Content(
                                    schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "권한 부여 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PostMapping("/set-role/{uid}")
    public ResponseEntity<String> setRole(@PathVariable String uid) {
        try {
            Map<String, Object> claims = Map.of("role", "ADMIN");
            firebaseAuth.setCustomUserClaims(uid, claims);
            return ResponseEntity.ok("관리자 권한이 부여되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("권한 부여 실패: " + e.getMessage());
        }
    }
}
