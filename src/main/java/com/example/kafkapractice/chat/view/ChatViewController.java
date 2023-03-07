package com.example.kafkapractice.chat.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ChatViewController {
    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }
}
