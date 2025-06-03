package com.example.find_people.controller;

import com.example.find_people.dto.BoardRequest;
import com.example.find_people.dto.BoardResponse;
import com.example.find_people.dto.MainResponse;
import com.example.find_people.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Tag(name = "게시글 API")
@RequestMapping("/api/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @Operation(
            summary = "게시글 등록 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "등록한 게시글",
                            content = @Content(
                                    schema = @Schema(implementation = BoardResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "게시글 등록 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PostMapping("/save")
    public ResponseEntity<BoardResponse> saveBoard(
            @RequestBody BoardRequest request) {
        try {
            return ResponseEntity.ok(boardService.saveBoard(request));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @Operation(
            summary = "게시글 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 조회",
                            content = @Content(
                                    schema = @Schema(implementation = BoardResponse.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "게시글 조회 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(
            @PathVariable long id) {
        try {
            return ResponseEntity.ok(boardService.getBoard(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @Operation(
            summary = "게시글 목록 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 목록 조회",
                            content = @Content(
                              array = @ArraySchema(schema = @Schema(implementation = BoardResponse.class)))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "게시글 조회 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @GetMapping("/list")
    public ResponseEntity<List<BoardResponse>> getBoardList() {
        try {
            return ResponseEntity.ok(boardService.getBoardList());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @Operation(
            summary = "게시글 삭제 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 삭제",
                            content = @Content(
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "게시글 삭제 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(
            @PathVariable Long id) {
        try {
            boardService.deleteBoard(id);
            return ResponseEntity.ok("게시글 삭제 완료");
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @Operation(
            summary = "게시글 참여 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 참여",
                            content = @Content(
                                    schema = @Schema(implementation = Boolean.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "게시글 참여 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            },
            security = {@SecurityRequirement(name = "accessToken")}
    )
    @PostMapping("/join")
    public ResponseEntity<Boolean> joinBoard(
            @RequestParam Long id) {
        try {
            return ResponseEntity.ok(boardService.joinTeam(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @Operation(
            summary = "게시글 참여 취소 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 참여 취소",
                            content = @Content(
                                    schema = @Schema(implementation = Boolean.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "게시글 참여 취소 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PostMapping("/leave")
    public ResponseEntity<Boolean> cancelJoinBoard(
            @RequestParam Long id) {
        try {
            return ResponseEntity.ok(boardService.leaveTeam(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    @Operation(
            summary = "메인페이지 카테고리, 게시글 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "메인페이지 게시글 조회 성공",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = MainResponse.class)))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "메인페이지 게시글 조회 실패",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @GetMapping("/main")
    public ResponseEntity<List<MainResponse>> getMainBoard() {
        try {
            return ResponseEntity.ok(boardService.getMainBoard());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }
}
