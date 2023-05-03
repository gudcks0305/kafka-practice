package com.example.email;

import com.example.kafkapractice.email_worker.domain.SendEmail;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class IMAPEmailResponse {
    public void readEmail(SendEmail sendEmail) throws MessagingException, IOException {
        Properties properties = new Properties();
        properties.put("mail.imap.host", "imap.naver.com");
        properties.put("mail.imap.port", 993);
        properties.put("mail.imap.starttls.enable", true);
        Session session = Session.getDefaultInstance(properties, sendEmail.getFrom().getAuthenticator());
        Store store = session.getStore("imap");
        store.connect(sendEmail.getFrom().getEmail(), sendEmail.getFrom().getPassword());
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        for (int i = 0; i < messages.length; i++) {
            System.out.println("Subject: " + messages[i].getSubject());
            System.out.println("From: " + messages[i].getFrom()[0]);
            System.out.println("Text: " + messages[i].getContent().toString());
        }
        inbox.close(false);
        store.close();
    }

    private Properties getProperties(SendEmail sendEmail) {
        Properties properties = new Properties();
        properties.put("mail.imap.starttls.enable", sendEmail.getFrom().is_tls());
        properties.put("mail.imap.host", sendEmail.getFrom().getHostname());
        properties.put("mail.imap.port", sendEmail.getFrom().getPort());
        return properties;
    }
}
