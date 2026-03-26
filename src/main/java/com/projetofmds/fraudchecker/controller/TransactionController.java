package com.projetofmds.fraudchecker.controller;

import com.projetofmds.fraudchecker.dto.TransactionRequestDTO;
import com.projetofmds.fraudchecker.dto.TransactionResponseDTO;
import com.projetofmds.fraudchecker.model.Transaction;
import com.projetofmds.fraudchecker.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions") // Simplificamos a rota principal
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody @Valid TransactionRequestDTO request) {
        
        // 1. Chamamos o service passando o DTO (isso vai dar erro no Service por enquanto)
        Transaction transaction = transactionService.processTransaction(request);
        
        // 2. Convertemos a Entidade para o Record de Resposta (Mapeamento manual)
        TransactionResponseDTO response = new TransactionResponseDTO(
            transaction.getId(),
            transaction.getAmount(),
            transaction.getTimestamp(),
            transaction.getStatus().name(),
            transaction.getBaseRiskScore()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/account/{accountId}") 
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable Long accountId) {
        List<Transaction> history = transactionService.getAccountHistory(accountId);
        return ResponseEntity.ok(history);
    }
}