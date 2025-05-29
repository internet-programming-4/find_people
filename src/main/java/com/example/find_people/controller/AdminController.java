package com.example.find_people.controller;

import com.example.find_people.entity.Category;
import com.example.find_people.entity.Post;
import com.example.find_people.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

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
}
