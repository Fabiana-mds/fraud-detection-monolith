package com.projetofmds.fraudchecker.service;

import com.projetofmds.fraudchecker.model.Account;
import com.projetofmds.fraudchecker.model.Transaction;
import com.projetofmds.fraudchecker.model.enums.TransactionStatus;
import com.projetofmds.fraudchecker.model.enums.TransactionType;
import com.projetofmds.fraudchecker.repository.AccountRepository;
import com.projetofmds.fraudchecker.repository.TransactionRepository;
import com.projetofmds.fraudchecker.strategy.RiskRuleChecker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.projetofmds.fraudchecker.dto.TransactionRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    
    // Spring injecao automatica das regras 
    private final List<RiskRuleChecker> riskRules;

    public Transaction processTransaction(TransactionRequest request) {
        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        Transaction transaction = Transaction.builder()
            .amount(request.amount())
            .type(TransactionType.valueOf(request.typeString()))
            .account(account)
            .timestamp(LocalDateTime.now())
            .build();

        // --- STRATEGY PATTERN ---
        // score base da conta
        BigDecimal totalRisk = account.getBaseRiskScore();

        // Soma do risco de cada regra dinamicamente
        for (RiskRuleChecker rule : riskRules) {
            totalRisk = totalRisk.add(rule.check(transaction));
        }
        
        transaction.setBaseRiskScore(totalRisk);
        // ------------------------------------

        // Decisão final
        if (totalRisk.compareTo(new BigDecimal("100")) > 0 || 
            transaction.getAmount().compareTo(new BigDecimal("10000")) > 0) {
            transaction.setStatus(TransactionStatus.REJECTED);
        } else {
            transaction.setStatus(TransactionStatus.APPROVED);
        }

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAccountHistory(Long accountId) {
    return transactionRepository.findByAccountId(accountId);
}
}