package com.taskmanager.service.impl;

import com.taskmanager.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendTaskAssignmentEmail(String toEmail, String taskTitle) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("New Task Assigned");
        message.setText(
                "You have been assigned a new task: " + taskTitle
        );

        mailSender.send(message);
    }
}
