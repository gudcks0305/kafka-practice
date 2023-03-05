package com.example.kafkapractice.color.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ColorViewController {
    @GetMapping("/color")
    public String color() {
        return "favorite-color-webpage";
    }
}
