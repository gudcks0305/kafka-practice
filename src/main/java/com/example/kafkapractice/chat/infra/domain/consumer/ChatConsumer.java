package com.example.kafkapractice.chat.infra.domain.consumer;

import com.example.kafkapractice.chat.domain.dto.Message;
import com.example.kafkapractice.chat.infra.config.kafka.KafkaChatConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatConsumer {
   private final SimpMessagingTemplate template;

   public ChatConsumer(SimpMessagingTemplate template) {
      this.template = template;
   }

   @KafkaListener(topics = KafkaChatConstants.KAFKA_TOPIC, groupId = KafkaChatConstants.GROUP_ID)
   public void listen(Message message) {
      log.info("sending via kafka listener..");
      log.info("Consume message : " + message);
      template.convertAndSend("/topic/group", message);
   }
}
