package com.github.xz37.common;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPage {
    private List<Transaction> transactions;
    private int pageSize;
    private int currentPage;
    private int totalItems;
    private int totalPages;

    public BigDecimal getTotalCredit() {
        return transactions.stream()
                .filter(Transaction::isCredit)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalDebit() {
        return transactions.stream()
                .filter(transaction -> !transaction.isCredit())
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
