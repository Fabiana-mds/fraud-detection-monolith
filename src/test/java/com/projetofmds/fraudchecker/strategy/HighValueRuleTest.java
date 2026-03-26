package com.projetofmds.fraudchecker.strategy;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import com.projetofmds.fraudchecker.model.Transaction;


class HighValueRuleTest {

    private final HighValueRule rule = new HighValueRule();

    @Test
    void shouldReturnSixtyPointsWhenAmountIsSixThousand() {
        Transaction tx = Transaction.builder()
                .amount(new BigDecimal("6000"))
                .build();

        BigDecimal score = rule.check(tx);

        // O segredo está aqui: compareTo retorna 0 se os valores forem numericamente iguais
        assertTrue(new BigDecimal("60").compareTo(score) == 0, 
            "Deveria retornar 60 pontos (6E+1). Recebido: " + score);
    }

    @Test
    void shouldReturnTenPointsWhenAmountIsOneThousand() {
        Transaction tx = Transaction.builder()
                .amount(new BigDecimal("1000"))
                .build();

        BigDecimal score = rule.check(tx);

        // Usando compareTo para evitar erro de escala (10 vs 1E+1)
        assertTrue(new BigDecimal("10").compareTo(score) == 0,
            "Deveria retornar 10 pontos (1E+1). Recebido: " + score);
    }
}