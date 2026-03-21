package com.projetofmds.fraudchecker.service.rules;

import com.projetofmds.fraudchecker.model.Transaction;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class HighValueRule implements RiskRuleChecker {

    @Override
    public BigDecimal check(Transaction transaction) {
        // Se a transação for maior que 5000, gera 50 pontos de risco
        if (transaction.getAmount().compareTo(new BigDecimal("5000")) > 0) {
            return new BigDecimal("50");
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String getRuleName() {
        return "VALOR_ACIMA_DO_LIMITE";
    }
}