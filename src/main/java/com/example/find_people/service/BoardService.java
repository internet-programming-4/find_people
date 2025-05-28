package com.example.find_people.service;
import com.example.find_people.dto.BoardRequest;
import com.example.find_people.dto.BoardResponse;
import com.example.find_people.entity.Board;
import com.example.find_people.entity.Participant;
import com.example.find_people.entity.User;
import com.example.find_people.entity.enums.BoardStatus;
import com.example.find_people.repository.BoardRepository;
import com.example.find_people.repository.ParticipantRepository;
import com.example.find_people.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;

    // 게시글 저장
    public BoardResponse saveBoard(BoardRequest request) {
        try {
            Board newBoard = new Board();
            newBoard.setTitle(request.getTitle());
            newBoard.setContent(request.getContent());
            newBoard.setStatus(BoardStatus.ACTIVE.toString());
            newBoard.setEndDate(request.getEndDate());
            newBoard.setNumber(request.getNumber());

            boardRepository.save(newBoard);

            return BoardResponse.builder()
                    .id(newBoard.getId())
                    .title(newBoard.getTitle())
                    .content(newBoard.getContent())
                    .writer(newBoard.getWriter().getName())
                    .status(BoardStatus.ACTIVE.toString())
                    .number(newBoard.getNumber())
                    .createdAt(newBoard.getCreatedAt())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("게시글 저장 실패 ", e);
        }
    }

    // 게시글 조회
    public BoardResponse getBoard(Long id) {
        try {
            final Optional<Board> board = boardRepository.findById(id);

            if(board.isEmpty()) {
                throw new RuntimeException("게시글 조회 실패");
            }

            return BoardResponse.builder()
                    .id(board.get().getId())
                    .title(board.get().getTitle())
                    .content(board.get().getContent())
                    .writer(board.get().getWriter().getName())
                    .status(board.get().getStatus())
                    .number(board.get().getNumber())
                    .createdAt(board.get().getCreatedAt())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("게시글 조회 실패 ", e);
        }
    }

    // 게시글 목록 조회
    public List<BoardResponse> getBoardList() {
        try {
            final List<Board> boardList = boardRepository.findAll();

            return boardList.stream().map((board) -> BoardResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getWriter().getName())
                    .status(board.getStatus())
                    .number(board.getNumber())
                    .createdAt(board.getCreatedAt())
                    .build()).toList();
        } catch (Exception e) {
            throw new RuntimeException("게시글 목록 조회 실패 ", e);
        }
    }

    // 게시글 삭제
    public void deleteBoard(Long id) {
        try {
            final Optional<Board> board = boardRepository.findById(id);

            final String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final Optional<User> user = userRepository.findByUid(uid);

            if(board.isEmpty() || user.isEmpty()) {
                throw new RuntimeException("게시글 또는 유저가 존재하지 않습니다.");
            }

            if(board.get().getWriter() != user.get()) {
                throw new RuntimeException("게시글 작성자가 아닙니다.");
            }

            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("게시글 조회 실패 ", e);
        }
    }

    // 참여
    public boolean joinTeam(Long boardId) {
        try {
            final Optional<Board> board = boardRepository.findById(boardId);

            final String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final Optional<User> user = userRepository.findByUid(uid);

            if(board.isEmpty() || user.isEmpty()) {
                throw new RuntimeException("게시글 또는 유저가 존재하지 않습니다.");
            }

            if(!isBeforeEndDate(boardId)) {
                // 게시글 참여기간 지났을 경우
                return false;
            }

            participantRepository.save(new Participant(board.get(), user.get()));
            return true;
        } catch (Exception e) {
            throw new RuntimeException("게시글 조회 실패 ", e);
        }
    }

    // 참여 취소
    public boolean leaveTeam(Long boardId) {
        try {
            final Optional<Board> board = boardRepository.findById(boardId);

            final String uid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final Optional<User> user = userRepository.findByUid(uid);

            if(board.isEmpty() || user.isEmpty()) {
                throw new RuntimeException("게시글 또는 유저가 존재하지 않습니다.");
            }

            participantRepository.deleteByUserAndBoard(user.get(), board.get());
            return true;
        } catch (Exception e) {
            throw new RuntimeException("참여 취소 실패 ", e);
        }
    }

    // 게시글 마감 전일 경우 ture
    private boolean isBeforeEndDate(Long boardId) {
        try {
            final Optional<Board> board = boardRepository.findById(boardId);

            if(board.isEmpty()) {
                throw new RuntimeException("존재하지 않는 게시글입니다.");
            }

            return board.get().getEndDate().isBefore(LocalDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException("존재하지 않는 게시글입니다. ", e);
        }
    }

}
