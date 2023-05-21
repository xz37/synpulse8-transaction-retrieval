package com.github.xz37.consumer.service;

import com.github.xz37.common.Transaction;
import com.github.xz37.common.TransactionPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {
    private TransactionService transactionService;

    @Mock
    private ConcurrentHashMap<UUID, Transaction> map;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(map);
    }

    @Test
    void findAll_ShouldReturnAllTransactions() {
        // Arrange
        Transaction transaction1 = Transaction.builder()
                .uuid(UUID.randomUUID())
                .amount(BigDecimal.valueOf(100))
                .currency("USD")
                .build();
        Transaction transaction2 = Transaction.builder()
                .uuid(UUID.randomUUID())
                .amount(BigDecimal.valueOf(200))
                .currency("EUR")
                .build();
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(map.values()).thenReturn(transactions);

        // Act
        TransactionPage result = transactionService.findAll(10, 1);

        // Assert
        assertEquals(2, result.getTotalItems());
        List<Transaction> resultTransactions = result.getTransactions();
        assertEquals(transactions, resultTransactions);
    }

    @Test
    void findByMonth_ShouldReturnFilteredTransactions() {
        // Arrange
        Transaction transaction1 = Transaction.builder()
                .uuid(UUID.randomUUID())
                .amount(BigDecimal.valueOf(100))
                .currency("USD")
                .build();
        Transaction transaction2 = Transaction.builder()
                .uuid(UUID.randomUUID())
                .amount(BigDecimal.valueOf(200))
                .currency("EUR")
                .build();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        Date valueDate = calendar.getTime();
        transaction1.setValueDate(valueDate);
        transaction2.setValueDate(valueDate);
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(map.values()).thenReturn(transactions);

        // Act
        TransactionPage result = transactionService.findByMonth(2023, 5, 10, 1);

        // Assert
        assertEquals(2, result.getTotalItems());
        List<Transaction> resultTransactions = result.getTransactions();
        assertEquals(transactions, resultTransactions);
    }

    @Test
    void findByYear_ShouldReturnFilteredTransactions() {
        // Arrange
        Transaction transaction1 = Transaction.builder()
                .uuid(UUID.randomUUID())
                .amount(BigDecimal.valueOf(100))
                .currency("USD")
                .build();
        Transaction transaction2 = Transaction.builder()
                .uuid(UUID.randomUUID())
                .amount(BigDecimal.valueOf(200))
                .currency("EUR")
                .build();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date valueDate = calendar.getTime();
        transaction1.setValueDate(valueDate);
        transaction2.setValueDate(valueDate);
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(map.values()).thenReturn(transactions);

        // Act
        TransactionPage result = transactionService.findByYear(2023, 10, 1);

        // Assert
        assertEquals(2, result.getTotalItems());
        List<Transaction> resultTransactions = result.getTransactions();
        assertEquals(transactions, resultTransactions);
    }
}