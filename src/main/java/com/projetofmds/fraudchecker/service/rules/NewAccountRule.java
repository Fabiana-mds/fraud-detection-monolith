package com.projetofmds.fraudchecker.service.rules;

import com.projetofmds.fraudchecker.model.Transaction;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class NewAccountRule implements RiskRuleChecker {

    @Override
    public BigDecimal check(Transaction transaction) {

        BigDecimal accountScore = transaction.getAccount().getBaseRiskScore();
        if (accountScore.compareTo(new BigDecimal("80")) >= 0) {
            return new BigDecimal("20");
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String getRuleName() {
        return "CONTA_COM_ALTO_RISCO_BASE";
    }
}