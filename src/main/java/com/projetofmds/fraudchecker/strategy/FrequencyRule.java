package com.projetofmds.fraudchecker.strategy;

import com.projetofmds.fraudchecker.model.Transaction;
import com.projetofmds.fraudchecker.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FrequencyRule implements RiskRuleChecker {

    private final TransactionRepository transactionRepository;

    @Override
    public BigDecimal check(Transaction transaction) {
        LocalDateTime umHoraAtras = LocalDateTime.now().minusMinutes(60);
        long count = transactionRepository.countTransactionsSince(
            transaction.getAccount().getId(), 
            umHoraAtras
        );

        if (count > 5) {
            long excesso = count - 5;
            return new BigDecimal(excesso).multiply(new BigDecimal("20"));
        }
        return BigDecimal.ZERO;
    }
}
