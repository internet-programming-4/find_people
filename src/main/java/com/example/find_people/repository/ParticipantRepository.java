package com.example.find_people.repository;

import com.example.find_people.entity.Board;
import com.example.find_people.entity.Participant;
import com.example.find_people.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    void deleteByUserAndBoard(User user, Board board);
}
