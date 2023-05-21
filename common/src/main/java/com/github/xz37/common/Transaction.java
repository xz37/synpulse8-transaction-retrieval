package com.github.xz37.common;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private UUID uuid;
    private BigDecimal amount;
    private String currency;
    private String accountIban;
    private String customerId;
    private Date valueDate;
    private String description;
    private boolean isCredit;

}
