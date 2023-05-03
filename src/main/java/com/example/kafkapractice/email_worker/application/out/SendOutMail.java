package com.example.kafkapractice.email_worker.application.out;

import com.example.kafkapractice.email_worker.domain.SendEmail;

public interface SendOutMail {
    public void send(SendEmail sendEmail);
}
