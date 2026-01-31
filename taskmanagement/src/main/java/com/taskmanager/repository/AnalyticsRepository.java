package com.taskmanager.repository;

import com.taskmanager.model.Task;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Map;

public interface AnalyticsRepository extends MongoRepository<Task, String> {

    @Aggregation(pipeline = {
            "{ $group: { _id: '$status', count: { $sum: 1 } } }"
    })
    List<Map<String, Object>> countTasksByStatus();

    @Aggregation(pipeline = {
            "{ $match: { assignedTo: { $ne: null } } }",
            "{ $group: { _id: '$assignedTo', count: { $sum: 1 } } }"
    })
    List<Map<String, Object>> countTasksPerUser();
}
