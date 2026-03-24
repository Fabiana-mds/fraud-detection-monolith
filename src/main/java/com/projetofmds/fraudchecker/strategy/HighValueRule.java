package com.projetofmds.fraudchecker.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.projetofmds.fraudchecker.model.Transaction;

@Component
public class HighValueRule implements RiskRuleChecker {
    @Override
    public BigDecimal check(Transaction transaction){

        return transaction.getAmount().divide(new BigDecimal("100.0"));
    }

}
