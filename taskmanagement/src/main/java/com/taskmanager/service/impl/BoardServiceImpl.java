package com.taskmanager.service.impl;

import com.taskmanager.dto.BoardRequest;
import com.taskmanager.dto.BoardResponse;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.model.Board;
import com.taskmanager.repository.BoardRepository;
import com.taskmanager.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private String getCurrentUserId() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof String) {
            return (String) principal;
        }

        throw new RuntimeException("Unauthenticated user");
    }


    @CacheEvict(value = "boards", allEntries = true)
    @Override
    public BoardResponse createBoard(BoardRequest request) {

        Board board = Board.builder()
                .name(request.getName())
                .ownerId(getCurrentUserId())
                .createdAt(LocalDateTime.now())
                .build();

        return mapToResponse(boardRepository.save(board));
    }


    @Cacheable(
            value = "boards",
            key = "#root.methodName + '_' + T(org.springframework.security.core.context.SecurityContextHolder).context.authentication.principal"
    )
    @Override
    public Page<BoardResponse> getMyBoards(Pageable pageable) {

        return boardRepository
                .findByOwnerId(getCurrentUserId(), pageable)
                .map(this::mapToResponse);
    }


    @Override
    public BoardResponse getBoardById(String boardId) {

        Board board = boardRepository
                .findByIdAndOwnerId(boardId, getCurrentUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Board not found"));

        return mapToResponse(board);
    }

    @CacheEvict(value = "boards", allEntries = true)
    @Override
    public BoardResponse updateBoard(String boardId, BoardRequest request) {

        Board board = boardRepository
                .findByIdAndOwnerId(boardId, getCurrentUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Board not found"));

        board.setName(request.getName());
        return mapToResponse(boardRepository.save(board));
    }


    @CacheEvict(value = "boards", allEntries = true)
    @Override
    public void deleteBoard(String boardId) {

        Board board = boardRepository
                .findByIdAndOwnerId(boardId, getCurrentUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Board not found"));

        boardRepository.delete(board);
    }

    private BoardResponse mapToResponse(Board board) {
        return BoardResponse.builder()
                .id(board.getId())
                .name(board.getName())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
