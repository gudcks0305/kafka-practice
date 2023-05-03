package com.example.email;

import com.example.kafkapractice.email_worker.domain.SendEmail;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailRequest {


    public void send(SendEmail sendEmail) throws MessagingException {
        Session session = Session.getInstance(getProperties(sendEmail), sendEmail.getFrom().getAuthenticator());
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(sendEmail.getFrom().getEmail()));
            mimeMessage.setRecipients(MimeMessage.RecipientType.TO, sendEmail.getToInternetAddress());
            mimeMessage.setRecipients(MimeMessage.RecipientType.CC, sendEmail.getCcInternetAddress());
            mimeMessage.setRecipients(MimeMessage.RecipientType.BCC, sendEmail.getBccInternetAddress());
            mimeMessage.setSubject(sendEmail.getContent().getSubject());
            mimeMessage.setText(sendEmail.getContent().getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Transport.send(mimeMessage);
    }

    private Properties getProperties(SendEmail sendEmail) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", sendEmail.getFrom().is_tls());
        properties.put("mail.smtp.host", sendEmail.getFrom().getHostname());
        properties.put("mail.smtp.port", sendEmail.getFrom().getPort());
        return properties;
    }

}
