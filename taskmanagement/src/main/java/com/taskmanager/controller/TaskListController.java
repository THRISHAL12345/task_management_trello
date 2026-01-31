package com.taskmanager.controller;

import com.taskmanager.dto.TaskListRequest;
import com.taskmanager.dto.TaskListResponse;
import com.taskmanager.service.TaskListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards/{boardId}/lists")
@RequiredArgsConstructor
public class TaskListController {

    private final TaskListService taskListService;

    @PostMapping
    public ResponseEntity<TaskListResponse> createList(
            @PathVariable String boardId,
            @Valid @RequestBody TaskListRequest request
    ) {
        return ResponseEntity.ok(taskListService.createList(boardId, request));
    }

    @GetMapping
    public ResponseEntity<List<TaskListResponse>> getLists(
            @PathVariable String boardId
    ) {
        return ResponseEntity.ok(taskListService.getListsByBoard(boardId));
    }

    @PutMapping("/{listId}")
    public ResponseEntity<TaskListResponse> updateList(
            @PathVariable String boardId,
            @PathVariable String listId,
            @Valid @RequestBody TaskListRequest request
    ) {
        return ResponseEntity.ok(
                taskListService.updateList(boardId, listId, request)
        );
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteList(
            @PathVariable String boardId,
            @PathVariable String listId
    ) {
        taskListService.deleteList(boardId, listId);
        return ResponseEntity.noContent().build();
    }
}
