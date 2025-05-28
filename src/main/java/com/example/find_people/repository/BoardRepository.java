package com.example.find_people.repository;

import com.example.find_people.entity.Board;
import com.example.find_people.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByCategory(Category category);
}
