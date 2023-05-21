package com.github.xz37.consumer.config;

import com.github.xz37.common.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MapConfig {

    @Bean
    public ConcurrentHashMap<UUID, Transaction> map() {
        ConcurrentHashMap<UUID, Transaction> map = new ConcurrentHashMap<>();

        // generate some fake transaction data
        for (int i = 0; i < 20; i++) {
            Transaction transaction = generateRandomTransaction();
            map.put(transaction.getUuid(), transaction);
        }

        return map;
    }

    private Transaction generateRandomTransaction() {
        Random random = new Random();

        UUID uuid = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(random.nextDouble() * 100);
        String currency = "CHF";
        String accountIban = "CH93-0000-0000-0000-0000-" + (random.nextInt(9) + 1);
        String customerId = "p-" + String.format("%09d", random.nextInt(1_000_000_000));
        Date valueDate = new Date();
        String description = "Transaction " + uuid.toString();
        boolean isCredit = random.nextBoolean();

        return Transaction.builder()
                .uuid(uuid)
                .amount(amount)
                .currency(currency)
                .accountIban(accountIban)
                .customerId(customerId)
                .valueDate(valueDate)
                .description(description)
                .isCredit(isCredit)
                .build();
    }
}
