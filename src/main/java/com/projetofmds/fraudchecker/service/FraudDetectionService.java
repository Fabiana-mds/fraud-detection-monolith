package com.projetofmds.fraudchecker.service;

import com.projetofmds.fraudchecker.model.Transaction;
import com.projetofmds.fraudchecker.model.enums.TransactionStatus;
import com.projetofmds.fraudchecker.service.rules.RiskRuleChecker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor // Injetar a lista de regras
public class FraudDetectionService {

    // Injeção de Dependência.
    private final List<RiskRuleChecker> rules;

    public Transaction analyzeTransaction(Transaction transaction) {
        BigDecimal totalScore = BigDecimal.ZERO;

        // Passa a transação por cada regra e vai somando o score
        for (RiskRuleChecker rule : rules) {
            totalScore = totalScore.add(rule.check(transaction));
        }

        transaction.setRiskScore(totalScore);

        // Lógica de decisão (Heurística simples)
        if (totalScore.compareTo(new BigDecimal("70")) >= 0) {
            transaction.setStatus(TransactionStatus.BLOCKED);
        } else if (totalScore.compareTo(new BigDecimal("30")) >= 0) {
            transaction.setStatus(TransactionStatus.SUSPICIOUS);
        } else {
            transaction.setStatus(TransactionStatus.APPROVED);
        }

        return transaction;
    }
}