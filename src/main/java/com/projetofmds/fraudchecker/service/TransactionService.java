package com.projetofmds.fraudchecker.service;

import com.projetofmds.fraudchecker.model.Account;
import com.projetofmds.fraudchecker.model.Transaction;
import com.projetofmds.fraudchecker.model.enums.TransactionStatus;
import com.projetofmds.fraudchecker.model.enums.TransactionType;
import com.projetofmds.fraudchecker.repository.AccountRepository;
import com.projetofmds.fraudchecker.repository.TransactionRepository;
import com.projetofmds.fraudchecker.strategy.RiskRuleChecker;
import com.projetofmds.fraudchecker.dto.TransactionEventDTO;
import com.projetofmds.fraudchecker.dto.TransactionRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final List<RiskRuleChecker> riskRules;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Transaction processTransaction(TransactionRequestDTO request) {
        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada!"));

        Transaction transaction = Transaction.builder()
            .amount(request.amount())
            .type(TransactionType.valueOf(request.typeString()))
            .account(account)
            .timestamp(LocalDateTime.now())
            .status(TransactionStatus.PENDING)
            .baseRiskScore(BigDecimal.ZERO)
            .build();

        Transaction saved = transactionRepository.save(transaction);

        eventPublisher.publishEvent(new TransactionEventDTO(
            saved.getId(), 
            account.getId(), 
            saved.getAmount()
        ));

        return saved;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void analyzeRisk(TransactionEventDTO event) {
        Transaction transaction = transactionRepository.findById(event.transactionId())
                .orElseThrow(() -> new RuntimeException("Transação não encontrada no processamento posterior"));
        
        Account account = transaction.getAccount();

        // --- STRATEGY PATTERN + APRENDIZADO ---
        // Risco que a conta já tinha
        BigDecimal totalRisk = account.getBaseRiskScore();

        // Soma dos risco das regras desta transação específica
        for (RiskRuleChecker rule : riskRules) {
            totalRisk = totalRisk.add(rule.check(transaction));
        }
        
        // Atualizacao do score na transação para auditoria
        transaction.setBaseRiskScore(totalRisk);

        // Decisão final baseada no score ACUMULADO
        if (totalRisk.compareTo(new BigDecimal("100")) > 0 || 
            transaction.getAmount().compareTo(new BigDecimal("10000")) > 0) {
            transaction.setStatus(TransactionStatus.REJECTED);
        } else {
            transaction.setStatus(TransactionStatus.APPROVED);
        }

        // --- APRENDIZADO ---
        // Atualizacao do score da CONTA para que a próxima transação já herde esse risco
        account.setBaseRiskScore(totalRisk);
        accountRepository.save(account); // <--- Salvando a "memória" da conta
        
        // Atualiza a transação no banco
        transactionRepository.save(transaction);
        
        log.info("📈 [APRENDIZADO] Novo Score da Conta " + account.getId() + ": " + totalRisk);
        log.info("⚡ [ASSÍNCRONO] Transação " + transaction.getId() + " finalizada como: " + transaction.getStatus());
    }

    public List<Transaction> getAccountHistory(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}