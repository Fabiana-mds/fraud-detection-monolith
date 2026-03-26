package com.projetofmds.fraudchecker.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.projetofmds.fraudchecker.model.RiskReport;
import com.projetofmds.fraudchecker.model.Transaction;
import com.projetofmds.fraudchecker.repository.RiskReportRepository;
import com.projetofmds.fraudchecker.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiskReportService {

    private final RiskReportRepository riskReportRepository;
    private final TransactionRepository transactionRepository;

    public RiskReport generateCurrentReport() {
        log.info("📊 Generating new risk report...");
        
        List<Transaction> allTransactions = transactionRepository.findAll();
        
        long total = allTransactions.size();
        long rejected = allTransactions.stream()
                .filter(t -> "REJECTED".equals(t.getStatus().toString()))
                .count();
                
        BigDecimal totalAmount = allTransactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        RiskReport report = RiskReport.builder()
                .generatedAt(LocalDateTime.now())
                .totalTransactions(total)
                .rejectedTransactions(rejected)
                .totalAmountProcessed(totalAmount)
                .build();

        log.info("✅ Report generated with {} transactions", total);
        return riskReportRepository.save(report);
    }

    public List<RiskReport> getAllReports() {
        return riskReportRepository.findAll();
    }
}