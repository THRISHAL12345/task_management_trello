package com.taskmanager.service;

import com.taskmanager.dto.TaskListRequest;
import com.taskmanager.dto.TaskListResponse;

import java.util.List;

public interface TaskListService {

    TaskListResponse createList(String boardId, TaskListRequest request);

    List<TaskListResponse> getListsByBoard(String boardId);

    TaskListResponse updateList(String boardId, String listId, TaskListRequest request);

    void deleteList(String boardId, String listId);
}
