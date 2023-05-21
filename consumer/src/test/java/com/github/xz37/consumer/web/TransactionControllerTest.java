package com.github.xz37.consumer.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xz37.common.Transaction;
import com.github.xz37.common.TransactionPage;
import com.github.xz37.consumer.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class TransactionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransactionController transactionController = new TransactionController(transactionService);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testFindAll() throws Exception {
        // Mock the TransactionPage response from the service
        TransactionPage transactionPage = new TransactionPage();
        transactionPage.setPageSize(10);
        transactionPage.setCurrentPage(1);
        // Mock the transactions list
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = Transaction.builder()
                .uuid(UUID.randomUUID())
                .amount(BigDecimal.valueOf(100))
                .currency("USD")
                .accountIban("1234567890")
                .customerId("ABC123")
                .build();
        transactions.add(transaction1);
        // Add more transactions as needed
        transactionPage.setTransactions(transactions);

        when(transactionService.findAll(10, 1)).thenReturn(transactionPage);

        // Perform the GET request and validate the response
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transaction")
                        .param("pageSize", "10")
                        .param("currentPage", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.transactions.length()").value(1)) // Adjust the length value based on the number of transactions
                .andReturn();

        verify(transactionService, times(1)).findAll(10, 1);
        verifyNoMoreInteractions(transactionService);
    }

    // Add more test methods for other endpoints and scenarios as needed
}