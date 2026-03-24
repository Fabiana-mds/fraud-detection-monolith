package com.projetofmds.fraudchecker.strategy;

import com.projetofmds.fraudchecker.model.Transaction;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class NightTimeRule implements RiskRuleChecker {
    @Override
    public BigDecimal check(Transaction transaction) {
        int hour = transaction.getTimestamp().getHour();
        // Se for entre 00h e 05h, retorna 20 pontos de risco
        if (hour >= 0 && hour <= 5) {
            return new BigDecimal("20");
        }
        return BigDecimal.ZERO;
    }
}