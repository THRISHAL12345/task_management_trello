package com.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskListRequest {

    @NotBlank(message = "List name is required")
    private String name;
}
