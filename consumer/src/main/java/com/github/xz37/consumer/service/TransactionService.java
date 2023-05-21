package com.github.xz37.consumer.service;

import com.github.xz37.common.Transaction;
import com.github.xz37.common.TransactionPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {
    private final ConcurrentHashMap<UUID, Transaction> map;

    public TransactionPage findAll(int pageSize, int currentPage) {
        List<Transaction> transactions = new ArrayList<>(map.values());
        return paginateTransactions(transactions, pageSize, currentPage);
    }

    public TransactionPage findByMonth(int year, int month, int pageSize, int currentPage) {
        List<Transaction> filteredTransactions = filterByMonth(year, month);
        return paginateTransactions(filteredTransactions, pageSize, currentPage);
    }

    public TransactionPage findByYear(int year, int pageSize, int currentPage) {
        List<Transaction> filteredTransactions = filterByYear(year);
        return paginateTransactions(filteredTransactions, pageSize, currentPage);
    }

    public void update(Transaction transaction) {
        if (map.containsKey(transaction.getUuid())) {
            map.replace(transaction.getUuid(), transaction);
            log.info("TransactionService.update " + transaction + " was updated!");
        } else {
            insert(transaction);
        }
    }

    public void delete(Transaction transaction) {
        map.remove(transaction.getUuid());
        log.info("TransactionService.delete " + transaction + " was deleted!");
    }

    public void insert(Transaction transaction) {
        map.put(transaction.getUuid(), transaction);
        log.info("TransactionService.insert " + transaction + " was inserted!");
    }

    private List<Transaction> filterByMonth(int year, int month) {
        return map.values().stream()
                .filter(t -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(t.getValueDate());
                    return cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) + 1 == month;
                })
                .sorted(Comparator.comparing(Transaction::getValueDate))
                .collect(Collectors.toList());
    }

    private List<Transaction> filterByYear(int year) {
        return map.values().stream()
                .filter(t -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(t.getValueDate());
                    return cal.get(Calendar.YEAR) == year;
                })
                .sorted(Comparator.comparing(Transaction::getValueDate))
                .collect(Collectors.toList());
    }

    private TransactionPage paginateTransactions(List<Transaction> transactions, int pageSize, int currentPage) {
        int totalItems = transactions.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        List<Transaction> paginatedTransactions = transactions.stream()
                .skip((long) (currentPage - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        return TransactionPage.builder()
                .transactions(paginatedTransactions)
                .pageSize(pageSize)
                .currentPage(currentPage)
                .totalItems(totalItems)
                .totalPages(totalPages)
                .build();
    }
}
