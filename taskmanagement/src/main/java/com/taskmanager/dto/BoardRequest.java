package com.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardRequest {

    @NotBlank(message = "Board name is required")
    private String name;
}
