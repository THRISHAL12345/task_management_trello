package com.taskmanager.repository;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {

    Page<Task> findByBoardId(String boardId, Pageable pageable);

    Page<Task> findByBoardIdAndStatus(
            String boardId,
            TaskStatus status,
            Pageable pageable
    );

    long countByStatus(TaskStatus status);
}

