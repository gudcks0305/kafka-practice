package com.example.kafkapractice.color.domain.service;

import org.springframework.stereotype.Service;

public interface FavoriteEventProduceService {
    void produceFavoriteEvent(String userName, String colorName,String userAgent);
}
