package com.example.kafkapractice.color.infra.service;

import com.example.kafkapractice.color.domain.service.FavoriteEventProduceService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FavoriteEventProduceServiceImpl implements FavoriteEventProduceService {
   private final KafkaTemplate<String, String> kafkaTemplate;

   public FavoriteEventProduceServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
      this.kafkaTemplate = kafkaTemplate;
   }
}
