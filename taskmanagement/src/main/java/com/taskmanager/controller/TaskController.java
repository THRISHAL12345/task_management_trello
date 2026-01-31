package com.taskmanager.controller;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/boards/{boardId}/lists/{listId}/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable String boardId,
            @PathVariable String listId,
            @Valid @RequestBody TaskRequest request
    ) {
        return ResponseEntity.ok(
                taskService.createTask(boardId, listId, request)
        );
    }

    @GetMapping("/boards/{boardId}/tasks")
    public ResponseEntity<Page<TaskResponse>> getTasks(
            @PathVariable String boardId,
            @RequestParam(required = false) TaskStatus status,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                taskService.getTasks(boardId, status, pageable)
        );
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable String taskId,
            @Valid @RequestBody TaskRequest request
    ) {
        return ResponseEntity.ok(
                taskService.updateTask(taskId, request)
        );
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/tasks/{taskId}/attachment",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<TaskResponse> uploadAttachment(
            @PathVariable String taskId,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(
                taskService.uploadAttachment(taskId, file)
        );
    }


}
