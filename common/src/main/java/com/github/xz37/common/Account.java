package com.github.xz37.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String iban;
    private String currency;
    private String customerId;
    private List<Transaction> transactions;
}
