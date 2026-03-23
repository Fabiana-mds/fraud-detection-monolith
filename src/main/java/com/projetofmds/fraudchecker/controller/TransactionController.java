package com.projetofmds.fraudchecker.controller;

import com.projetofmds.fraudchecker.model.Transaction;
import com.projetofmds.fraudchecker.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            @PathVariable Long accountId, 
            @RequestBody Transaction transaction) {
        
        Transaction savedTransaction = transactionService.processTransaction(accountId, transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Transaction> getHistory(@PathVariable Long accountId) {
        return transactionService.getAccountHistory(accountId);
    }
}