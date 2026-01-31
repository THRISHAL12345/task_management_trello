package com.taskmanager.controller;

import com.taskmanager.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/tasks-by-status")
    public ResponseEntity<List<Map<String, Object>>> tasksByStatus() {
        return ResponseEntity.ok(analyticsService.tasksByStatus());
    }

    @GetMapping("/tasks-per-user")
    public ResponseEntity<List<Map<String, Object>>> tasksPerUser() {
        return ResponseEntity.ok(analyticsService.tasksPerUser());
    }

    @GetMapping("/tasks")
    public ResponseEntity<Map<String, Long>> taskAnalytics() {
        return ResponseEntity.ok(analyticsService.getTaskAnalytics());
    }
}
