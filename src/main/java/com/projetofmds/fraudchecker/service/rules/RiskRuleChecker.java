package com.projetofmds.fraudchecker.service.rules;

import com.projetofmds.fraudchecker.model.Transaction;
import java.math.BigDecimal;

public interface RiskRuleChecker {
    // Retorna o score de risco que essa regra encontrou (0 a 100)
    BigDecimal check(Transaction transaction);

    String getRuleName();
}
