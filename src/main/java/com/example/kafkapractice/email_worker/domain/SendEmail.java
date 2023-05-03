package com.example.kafkapractice.email_worker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.mail.PasswordAuthentication;
import javax.mail.Authenticator;
import javax.mail.internet.InternetAddress;
import java.util.List;
@Getter
@AllArgsConstructor
public class SendEmail {
    private From from;
    private List<To> to;
    private List<To> cc;
    private List<To> bcc;
    private Content content;

    public InternetAddress[] getToInternetAddress() {
        return to.stream().map(to -> {
            try {
                return new InternetAddress(to.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).toArray(InternetAddress[]::new);
    }
    public InternetAddress[]  getCcInternetAddress() {
        return cc.stream().map(cc -> {
            try {
                return new InternetAddress(cc.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).toArray(InternetAddress[]::new);
    }
    public InternetAddress[]  getBccInternetAddress() {
        return bcc.stream().map(bcc -> {
            try {
                return new InternetAddress(bcc.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).toArray(InternetAddress[]::new);
    }
    @Getter
    @AllArgsConstructor
    public static class From {
        private String email;
        private String name;
        private String password;
        private String domain;
        private String hostname;
        private int port;
        private String protocol;
        private boolean is_tls;

        public Authenticator getAuthenticator() {
            return new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            };
        }


    }
    @Getter
    @AllArgsConstructor
    public static class To {
        private String email;
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public static class Content {
        private String subject;
        private String body;
        private List<Attachment> attachments;
    }

    @Getter
    @AllArgsConstructor
    public static class Attachment {
        private String filename;
        private String filePath;
    }

}

