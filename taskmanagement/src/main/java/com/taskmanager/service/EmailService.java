package com.taskmanager.service;

public interface EmailService {

    void sendTaskAssignmentEmail(String toEmail, String taskTitle);
}
