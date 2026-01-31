package com.taskmanager.service;

import com.taskmanager.dto.BoardRequest;
import com.taskmanager.dto.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    BoardResponse createBoard(BoardRequest request);

    Page<BoardResponse> getMyBoards(Pageable pageable);

    BoardResponse getBoardById(String boardId);

    BoardResponse updateBoard(String boardId, BoardRequest request);

    void deleteBoard(String boardId);
}
