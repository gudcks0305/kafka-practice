package com.example.kafkapractice.email_worker.config;

import com.example.kafkapractice.email_worker.application.out.SendOutMail;
import com.example.kafkapractice.email_worker.application.service.SendOutMailKafka;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailWorkerConfig {
   @Bean
   public SendOutMail sendOutMail(){
      return new SendOutMailKafka();
   }
}
