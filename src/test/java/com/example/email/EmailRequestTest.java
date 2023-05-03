package com.example.email;

import com.example.kafkapractice.email_worker.domain.SendEmail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

class EmailRequestTest {

    private EmailRequest emailRequest = new EmailRequest();

    @Test
    @DisplayName("Test EmailRequest - hiworks naver smtp")
    void testEmailRequestGmail() {
        SendEmail sendEmail = new SendEmail(
                //new SendEmail.From("hc.yoo@gritstandard.com", "hi", "필수", "smtp.hiworks.com", "smtp.hiworks.com", 143, "smtp", true),
                new SendEmail.From("gudcks0305@naver.com", "hi", "필수", "smtp.naver.com", "smtp.naver.com", 587, "smtp", true),

                List.of(new SendEmail.To("gudcks305@gmail.com", "test")),
                List.of(new SendEmail.To("gudcksbusi@gmail.com", "test")),
                List.of(new SendEmail.To("gudcks0305@naver.com", "test")),
                new SendEmail.Content("test", "test", null)
        );
        try {
            emailRequest.send(sendEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
