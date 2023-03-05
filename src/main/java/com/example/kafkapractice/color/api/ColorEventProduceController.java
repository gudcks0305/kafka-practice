package com.example.kafkapractice.color.api;

import com.example.kafkapractice.color.domain.service.FavoriteEventProduceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api")
public class ColorEventProduceController {
    private final FavoriteEventProduceService favoriteEventProduceService;

    public ColorEventProduceController(FavoriteEventProduceService favoriteEventProduceService) {
        this.favoriteEventProduceService = favoriteEventProduceService;
    }
    @GetMapping("/select-color")
    public void selectColor(@RequestHeader("user-agent") String userAgent ,
                            @RequestParam("user") String userName,
                            @RequestParam("color") String colorName) {
        log.info("userAgent: {}", userAgent);
        favoriteEventProduceService.produceFavoriteEvent(userName, colorName, userAgent);
    }
}
