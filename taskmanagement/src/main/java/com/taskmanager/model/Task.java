package com.taskmanager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    private String title;
    private String description;

    private TaskStatus status;
    private TaskPriority priority;

    private String listId;        // Parent list
    private String boardId;       // For fast ownership checks

    private String assignedTo;    // User ID (optional)

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;

    private String attachmentPath;
}
