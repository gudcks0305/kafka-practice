package com.example.kafkapractice.email_worker.application.service;

import com.example.kafkapractice.email_worker.application.out.SendOutMail;
import com.example.kafkapractice.email_worker.domain.SendEmail;

public class SendOutMailKafka implements SendOutMail {

    @Override
    public void send(SendEmail sendEmail) {
        System.out.println("SendOutMailKafka.send");
    }
}
