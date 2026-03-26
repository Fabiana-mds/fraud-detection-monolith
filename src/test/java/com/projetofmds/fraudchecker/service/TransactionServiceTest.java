package com.projetofmds.fraudchecker.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList; // Adicionado
import java.util.List;      // Adicionado
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy; // Adicionado
import org.mockito.junit.jupiter.MockitoExtension;

import com.projetofmds.fraudchecker.dto.TransactionEventDTO;
import com.projetofmds.fraudchecker.model.Account;
import com.projetofmds.fraudchecker.model.Transaction;
import com.projetofmds.fraudchecker.model.enums.TransactionStatus;
import com.projetofmds.fraudchecker.repository.AccountRepository;
import com.projetofmds.fraudchecker.repository.TransactionRepository;
import com.projetofmds.fraudchecker.strategy.RiskRuleChecker;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    // Criamos uma lista real (vazia por enquanto) para injetar no serviço
    @Spy
    private List<RiskRuleChecker> riskRules = new ArrayList<>();

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldRejectTransactionWhenAmountIsTooHigh() {
        // Cenário
        Long txId = 1L;
        Account account = new Account();
        account.setId(1L);
        account.setBaseRiskScore(BigDecimal.ZERO);

        Transaction transaction = new Transaction();
        transaction.setId(txId);
        transaction.setAmount(new BigDecimal("20000")); // Acima de 10.000
        transaction.setAccount(account);
        transaction.setStatus(TransactionStatus.PENDING);

        when(transactionRepository.findById(txId)).thenReturn(Optional.of(transaction));

        // Ação
        transactionService.analyzeRisk(new TransactionEventDTO(txId, 1L, transaction.getAmount()));

        // Verificação
        assertEquals(TransactionStatus.REJECTED, transaction.getStatus(), "Transação de 20k deve ser rejeitada");
    }
}