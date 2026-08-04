package com.neeraj.assistant.mail.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendPasswordResetEmail(
            String to,
            String resetLink
    ) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);

        message.setTo(to);

        message.setSubject("Reset Your Password");

        message.setText(
                """
                Hello,

                We received a request to reset your password.

                Click the link below:

                %s

                This link expires in 15 minutes.

                If you didn't request this, please ignore this email.

                AI Document Assistant
                """.formatted(resetLink)
        );

        mailSender.send(message);

    }

}