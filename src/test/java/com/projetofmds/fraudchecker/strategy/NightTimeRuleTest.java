package com.projetofmds.fraudchecker.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import com.projetofmds.fraudchecker.model.Transaction;

class NightTimeRuleTest {

    private final NightTimeRule rule = new NightTimeRule();

    @Test
    void shouldReturnTwentyPointsDuringNightTime() {
        // Cenário: 03:00 da manhã 
        Transaction tx = Transaction.builder()
                .timestamp(LocalDateTime.of(2024, 10, 10, 3, 0))
                .build();

        BigDecimal score = rule.check(tx);

        assertEquals(new BigDecimal("20"), score, "Horário noturno deve somar 20 pontos");
    }

    @Test
    void shouldReturnZeroPointsDuringDayTime() {
        // Cenário: 14:00 da tarde
        Transaction tx = Transaction.builder()
                .timestamp(LocalDateTime.of(2024, 10, 10, 14, 0))
                .build();

        BigDecimal score = rule.check(tx);

        assertEquals(BigDecimal.ZERO, score);
    }
}