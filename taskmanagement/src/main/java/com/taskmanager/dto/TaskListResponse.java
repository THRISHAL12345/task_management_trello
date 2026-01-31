package com.taskmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskListResponse {

    private String id;
    private String name;
    private int position;
}
