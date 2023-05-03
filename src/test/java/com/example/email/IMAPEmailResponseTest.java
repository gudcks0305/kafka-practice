package com.example.email;

import com.example.kafkapractice.email_worker.domain.SendEmail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

class IMAPEmailResponseTest {
    @Test
    @DisplayName("Test IMAPEmailResponse")
    void testIMAPEmailResponse() throws Exception {
        System.out.println("-- IMAP Emal 가져오기: Start\n\n");
        String host = "imap.naver.com"; //imap 호스트 주소. ex) imap.gmail.com
        String userEmail = "gudcks0305@naver.com"; //유저 이메일 주소
        String password = "필수"; //유저 암호
        IMAPMailService mailService = new IMAPMailService();
        mailService.login(host, userEmail, password);
        int messageCount = mailService.getMessageCount();
        //테스트 목적이라서 5개 초과이면 5개만 처리: TODO 삭제
        /*if (messageCount > 5) {
            messageCount = 5;
        }*/
        Message[] msgArray = mailService.getMessages(false);
        for (int i = messageCount - 5; i < messageCount; i++) {
            Message msg = msgArray[i];
            if (msg.getSubject() != null && msg.getFrom()[0] != null && msg.getContent() != null) {
                System.out.println(String.format("컨텐츠타임: %s", msg.getContentType()));
                System.out.println("발신자들"  + Arrays.toString(msg.getFrom()));
                System.out.println("cc" + Arrays.toString(msg.getRecipients(Message.RecipientType.CC)));
                System.out.println("bcc" + Arrays.toString(msg.getRecipients(Message.RecipientType.BCC)));
                System.out.println(String.format("메일제목: %s", msg.getSubject()));
                String mailText = mailService.getEmalText(msg.getContent());
                System.out.println(String.format("메일내용: %s", mailText));
                // message 에서 file 다운로드

            }
        }
        mailService.logout(); //로그아웃
        System.out.println("\n\n-- IMAP Emal 가져오기: 종료");
    }

    class IMAPMailService {
        private Session session;
        private Store store;
        private Folder folder;
        // hardcoding protocol and the folder
        // it can be parameterized and enhanced as required
        private String protocol = "imaps";
        private String file = "INBOX";

        public IMAPMailService() {
        }

        public boolean isLoggedIn() {
            return store.isConnected();
        }

        /**
         * 메일 본문 텍스트 내용을 가져옴
         *
         * @param content
         * @return
         * @throws Exception
         */
        public String getEmalText(Object content) throws Exception {
            //TODO: 개발 필요
            System.out.println("####  컨텐츠 타입에 따라서 text body 또는 멀티파트 처리 기능 구현이 필요");
            if (content instanceof Multipart) {
                System.out.println("Multipart 이메일임");
                System.out.println(((Multipart) content).getContentType());
            } else {
                System.out.println(content);
            }
            return null;
        }

        /**
         * to login to the mail host server
         */
        public void login(String host, String username, String password) throws Exception {
            URLName url = new URLName(protocol, host, 993, file, username, password);
            if (session == null) {
                Properties props = null;
                try {
                    props = System.getProperties();
                } catch (SecurityException sex) {
                    props = new Properties();
                }
                session = Session.getInstance(props, null);
            }
            store = session.getStore(url);
            store.connect(username, password);
            folder = store.getFolder("inbox"); //inbox는 받은 메일함을 의미
            //folder.open(Folder.READ_WRITE);
            folder.open(Folder.READ_ONLY); //읽기 전용
        }

        /**
         * to logout from the mail host server
         */
        public void logout() throws MessagingException {
            folder.close(false);
            store.close();
            store = null;
            session = null;
        }

        public int getMessageCount() {
            //TODO: 안 읽은 메일의 건수만 조회하는 기능 추가
            int messageCount = 0;
            try {
                messageCount = folder.getMessageCount();
            } catch (MessagingException me) {
                me.printStackTrace();
            }
            return messageCount;
        }

        /**
         * 이메일 리스트를 가져옴
         *
         * @param onlyNotRead 안읽은 메일 리스트만 가져올지 여부
         * @return
         * @throws MessagingException
         */
        public Message[] getMessages(boolean onlyNotRead) throws MessagingException {
            if (onlyNotRead) {
                return folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            } else {
                return folder.getMessages();
            }
        }
    }
}
