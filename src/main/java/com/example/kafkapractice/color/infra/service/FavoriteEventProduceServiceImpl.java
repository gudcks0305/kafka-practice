package com.example.kafkapractice.color.infra.service;

import com.example.kafkapractice.color.domain.event.FavoritColorEventVO;
import com.example.kafkapractice.color.domain.service.FavoriteEventProduceService;
import com.google.gson.Gson;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FavoriteEventProduceServiceImpl implements FavoriteEventProduceService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    public FavoriteEventProduceServiceImpl(KafkaTemplate<String, String> kafkaTemplate, Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
        this.gson = gson;
    }

    @Override
    public void produceFavoriteEvent(String userName, String colorName, String userAgent) {
        FavoritColorEventVO favoritColorEventVO = new FavoritColorEventVO(userName, colorName, String.valueOf(System.currentTimeMillis()), userAgent);
        String json = gson.toJson(favoritColorEventVO);
        kafkaTemplate.send("favorite-color", json);
    }
}
