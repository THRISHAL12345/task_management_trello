package com.taskmanager.repository;

import com.taskmanager.model.TaskList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskListRepository extends MongoRepository<TaskList, String> {

    List<TaskList> findByBoardIdOrderByPositionAsc(String boardId);

    Optional<TaskList> findByIdAndBoardId(String id, String boardId);

    long countByBoardId(String boardId);
}
