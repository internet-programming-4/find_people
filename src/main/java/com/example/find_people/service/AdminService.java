package com.example.find_people.service;

import com.example.find_people.dto.BoardResponse;
import com.example.find_people.entity.Board;
import com.example.find_people.entity.Category;
import com.example.find_people.entity.enums.BoardStatus;
import com.example.find_people.repository.BoardRepository;
import com.example.find_people.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

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
    public BoardResponse deactivateBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.setStatus(BoardStatus.INACTIVE.toString());
        boardRepository.save(board);
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter().getName())
                .status(board.getStatus())
                .number(board.getNumber())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
