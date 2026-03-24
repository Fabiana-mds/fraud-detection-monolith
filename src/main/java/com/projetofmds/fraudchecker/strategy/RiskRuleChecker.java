package com.projetofmds.fraudchecker.strategy;


import java.math.BigDecimal;
import com.projetofmds.fraudchecker.model.Transaction;

public interface RiskRuleChecker {
    BigDecimal check(Transaction transaction);

}
