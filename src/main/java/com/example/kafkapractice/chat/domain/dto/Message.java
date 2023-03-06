package com.example.kafkapractice.chat.domain.dto;

import lombok.Getter;

@Getter
public class Message implements java.io.Serializable{
    private String author;
    private String content;
    private String timestamp;

    public Message() {
    }
    public Message(String author, String content, String timestamp) {
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String toString() {
        return "Message(author=" + this.getAuthor() + ", content=" + this.getContent() + ", timestamp=" + this.getTimestamp() + ")";
    }
}
