package com.taskmanager.service.impl;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.repository.BoardRepository;
import com.taskmanager.repository.TaskListRepository;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.service.EmailService;
import com.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.taskmanager.util.FileStorageUtil;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final BoardRepository boardRepository;
    private final TaskListRepository taskListRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final FileStorageUtil fileStorageUtil;

    private String getCurrentUserId() {
        return (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    private void validateBoardOwnership(String boardId) {
        boardRepository
                .findByIdAndOwnerId(boardId, getCurrentUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Board not found"));
    }

    @Override
    public TaskResponse createTask(
            String boardId,
            String listId,
            TaskRequest request
    ) {
        validateBoardOwnership(boardId);

        taskListRepository
                .findByIdAndBoardId(listId, boardId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("List not found"));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null
                        ? request.getStatus()
                        : TaskStatus.TODO)
                .priority(request.getPriority())
                .assignedTo(request.getAssignedTo())
                .dueDate(request.getDueDate())
                .listId(listId)
                .boardId(boardId)
                .createdAt(LocalDateTime.now())
                .build();

        return map(taskRepository.save(task));
    }

    @Override
    public Page<TaskResponse> getTasks(
            String boardId,
            TaskStatus status,
            Pageable pageable
    ) {
        validateBoardOwnership(boardId);

        Page<Task> tasks = (status == null)
                ? taskRepository.findByBoardId(boardId, pageable)
                : taskRepository.findByBoardIdAndStatus(
                boardId, status, pageable);

        return tasks.map(this::map);
    }

    // 🔥 THIS METHOD IS THE IMPORTANT FIX
    @Override
    public TaskResponse updateTask(String taskId, TaskRequest request) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        validateBoardOwnership(task.getBoardId());

        String oldAssignee = task.getAssignedTo();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setAssignedTo(request.getAssignedTo());
        task.setDueDate(request.getDueDate());

        Task updatedTask = taskRepository.save(task);

        // 🔔 Email must NEVER break API
        if (request.getAssignedTo() != null &&
                !request.getAssignedTo().equals(oldAssignee)) {

            try {
                userRepository.findById(request.getAssignedTo())
                        .ifPresent(user ->
                                emailService.sendTaskAssignmentEmail(
                                        user.getEmail(),
                                        updatedTask.getTitle()
                                )
                        );
            } catch (Exception e) {
                // log only — DO NOT fail request
                System.err.println("Email sending failed: " + e.getMessage());
            }
        }

        return map(updatedTask);
    }

    @Override
    public TaskResponse uploadAttachment(String taskId, MultipartFile file) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        validateBoardOwnership(task.getBoardId());

        try {
            String path = fileStorageUtil.saveFile(file);
            task.setAttachmentPath(path);
            return map(taskRepository.save(task));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }



    @Override
    public void deleteTask(String taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        validateBoardOwnership(task.getBoardId());

        taskRepository.delete(task);
    }

    private TaskResponse map(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .assignedTo(task.getAssignedTo())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .attachmentPath(task.getAttachmentPath())
                .build();
    }
}
