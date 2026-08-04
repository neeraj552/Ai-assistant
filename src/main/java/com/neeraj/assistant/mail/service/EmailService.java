package com.neeraj.assistant.mail.service;

public interface EmailService {

    void sendPasswordResetEmail(
            String to,
            String resetLink
    );

}