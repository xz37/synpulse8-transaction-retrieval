package com.github.xz37.consumer.kafka;

import com.github.xz37.common.KafkaHeaders;
import com.github.xz37.common.KafkaTopics;
import com.github.xz37.common.Transaction;
import com.github.xz37.consumer.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TransactionListener {
    private final TransactionService service;

    @KafkaListener(topics = KafkaTopics.TRANSACTION_TOPIC, groupId = "xianzi")
    public void listen(
            @Payload Transaction transaction,
            @Header(KafkaHeaders.CONSUMER_OPERATION) String operation) {
        switch (operation) {
            case "update" -> service.update(transaction);
            case "delete" -> service.delete(transaction);
            case "insert" -> service.insert(transaction);
            default -> log.warn("TransactionListener.listen has an invalid CONSUMER_OPERATION header " + operation);
        }
        ;
    }
}
