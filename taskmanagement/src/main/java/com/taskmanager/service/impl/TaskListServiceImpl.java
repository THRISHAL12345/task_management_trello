package com.taskmanager.service.impl;

import com.taskmanager.dto.TaskListRequest;
import com.taskmanager.dto.TaskListResponse;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.model.Board;
import com.taskmanager.model.TaskList;
import com.taskmanager.repository.BoardRepository;
import com.taskmanager.repository.TaskListRepository;
import com.taskmanager.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;
    private final BoardRepository boardRepository;

    private String getCurrentUserId() {
        return (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private Board validateBoardOwnership(String boardId) {
        return boardRepository
                .findByIdAndOwnerId(boardId, getCurrentUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Board not found"));
    }

    @Override
    public TaskListResponse createList(String boardId, TaskListRequest request) {

        validateBoardOwnership(boardId);

        int position = (int) taskListRepository.countByBoardId(boardId);

        TaskList list = TaskList.builder()
                .name(request.getName())
                .boardId(boardId)
                .position(position)
                .createdAt(LocalDateTime.now())
                .build();

        return map(taskListRepository.save(list));
    }

    @Override
    public List<TaskListResponse> getListsByBoard(String boardId) {

        validateBoardOwnership(boardId);

        return taskListRepository
                .findByBoardIdOrderByPositionAsc(boardId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public TaskListResponse updateList(
            String boardId,
            String listId,
            TaskListRequest request
    ) {
        validateBoardOwnership(boardId);

        TaskList list = taskListRepository
                .findByIdAndBoardId(listId, boardId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("List not found"));

        list.setName(request.getName());

        return map(taskListRepository.save(list));
    }

    @Override
    public void deleteList(String boardId, String listId) {

        validateBoardOwnership(boardId);

        TaskList list = taskListRepository
                .findByIdAndBoardId(listId, boardId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("List not found"));

        taskListRepository.delete(list);
    }

    private TaskListResponse map(TaskList list) {
        return TaskListResponse.builder()
                .id(list.getId())
                .name(list.getName())
                .position(list.getPosition())
                .build();
    }
}
