package com.taskmanager.controller;

import com.taskmanager.dto.BoardRequest;
import com.taskmanager.dto.BoardResponse;
import com.taskmanager.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(
            @Valid @RequestBody BoardRequest request
    ) {
        return ResponseEntity.ok(boardService.createBoard(request));
    }

    @GetMapping
    public ResponseEntity<Page<BoardResponse>> getMyBoards(Pageable pageable) {
        return ResponseEntity.ok(boardService.getMyBoards(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable String id) {
        return ResponseEntity.ok(boardService.getBoardById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(
            @PathVariable String id,
            @Valid @RequestBody BoardRequest request
    ) {
        return ResponseEntity.ok(boardService.updateBoard(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable String id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
