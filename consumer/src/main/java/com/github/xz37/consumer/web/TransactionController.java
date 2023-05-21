package com.github.xz37.consumer.web;


import com.github.xz37.common.TransactionPage;
import com.github.xz37.consumer.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/transaction")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @GetMapping
    public ResponseEntity<TransactionPage> findAll(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int currentPage
    ) {
        return ResponseEntity.ok(service.findAll(pageSize, currentPage));
    }

    @GetMapping("{year}/{month}")
    public ResponseEntity<TransactionPage> findByMonth(
            @PathVariable int year,
            @PathVariable int month,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int currentPage
    ) {
        return ResponseEntity.ok(service.findByMonth(year, month, pageSize, currentPage));
    }

    @GetMapping("{year}")
    public ResponseEntity<TransactionPage> findByYear(
            @PathVariable int year,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int currentPage
    ) {
        return ResponseEntity.ok(service.findByYear(year, pageSize, currentPage));
    }
}

