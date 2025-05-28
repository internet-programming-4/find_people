package com.example.find_people.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Participant(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}
