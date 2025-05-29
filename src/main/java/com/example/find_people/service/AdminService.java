package com.example.find_people.service;

import com.example.find_people.entity.Category;
import com.example.find_people.entity.Post;
import com.example.find_people.repository.CategoryRepository;
import com.example.find_people.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;

    public AdminService(CategoryRepository categoryRepository, PostRepository postRepository) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
    }

    public Category addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, String newName) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(newName);
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 게시글 비활성화
    public Post deactivatePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setActive(false);
        return postRepository.save(post);
    }
}
