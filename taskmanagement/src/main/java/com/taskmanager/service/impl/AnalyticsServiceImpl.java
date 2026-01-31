package com.taskmanager.service.impl;

import com.taskmanager.model.TaskStatus;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.AnalyticsRepository;
import com.taskmanager.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsRepository analyticsRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<Map<String, Object>> tasksByStatus() {
        return analyticsRepository.countTasksByStatus();
    }

    @Override
    public List<Map<String, Object>> tasksPerUser() {
        return analyticsRepository.countTasksPerUser();
    }


    @Override
    public Map<String, Long> getTaskAnalytics() {

        long total = taskRepository.count();
        long todo = taskRepository.countByStatus(TaskStatus.TODO);
        long inProgress = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long done = taskRepository.countByStatus(TaskStatus.DONE);

        return Map.of(
                "totalTasks", total,
                "todoTasks", todo,
                "inProgressTasks", inProgress,
                "completedTasks", done
        );
    }
}
