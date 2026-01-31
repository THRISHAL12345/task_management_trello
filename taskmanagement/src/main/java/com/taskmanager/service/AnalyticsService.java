package com.taskmanager.service;

import java.util.List;
import java.util.Map;

public interface AnalyticsService {

    List<Map<String, Object>> tasksByStatus();

    List<Map<String, Object>> tasksPerUser();

    Map<String, Long> getTaskAnalytics();
}
