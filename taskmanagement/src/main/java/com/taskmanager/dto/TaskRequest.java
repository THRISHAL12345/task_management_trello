package com.taskmanager.dto;

import com.taskmanager.model.TaskPriority;
import com.taskmanager.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {

    @NotBlank
    private String title;

    private String description;

    private TaskStatus status;
    private TaskPriority priority;

    private String assignedTo;

    private LocalDateTime dueDate;
}
