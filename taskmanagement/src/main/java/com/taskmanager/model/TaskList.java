package com.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "task_lists")
public class TaskList {

    @Id
    private String id;

    private String name;

    // Parent board
    private String boardId;

    // Ordering inside board (0,1,2...)
    private int position;

    private LocalDateTime createdAt;
}
