package com.taskmanager.service;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface TaskService {

    TaskResponse createTask(String boardId, String listId, TaskRequest request);

    Page<TaskResponse> getTasks(
            String boardId,
            TaskStatus status,
            Pageable pageable
    );

    TaskResponse updateTask(String taskId, TaskRequest request);

    void deleteTask(String taskId);

    // 📎 REQUIRED METHOD
    TaskResponse uploadAttachment(String taskId, MultipartFile file);
}
