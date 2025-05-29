package com.example.find_people.controller;

import com.example.find_people.entity.Category;
import com.example.find_people.entity.Post;
import com.example.find_people.service.AdminService;
import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final AdminService adminService;
    private final FirebaseAuth firebaseAuth;

    @PostMapping("/category")
    public Category addCategory(@RequestParam String name) {
        return adminService.addCategory(name);
    }

    @PutMapping("/category/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestParam String name) {
        return adminService.updateCategory(id, name);
    }

    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return adminService.getAllCategories();
    }

    @PutMapping("/post/{id}/deactivate")
    public Post deactivatePost(@PathVariable Long id) {
        return adminService.deactivatePost(id);
    }

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
