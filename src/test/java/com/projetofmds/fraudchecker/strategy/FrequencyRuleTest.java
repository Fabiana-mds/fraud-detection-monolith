package com.projetofmds.fraudchecker.strategy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projetofmds.fraudchecker.model.Account;
import com.projetofmds.fraudchecker.model.Transaction;
import com.projetofmds.fraudchecker.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class FrequencyRuleTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private FrequencyRule rule;

    @Test
    void shouldReturnRiskScoreWhenFrequencyIsHigh() {
        // Cenário: Conta ID 1 com 7 transações na última hora (limite é 5)
        Account account = new Account();
        account.setId(1L);
        Transaction tx = Transaction.builder().account(account).build();

        // Simulamos que o banco encontrou 7 transações
        when(transactionRepository.countTransactionsSince(eq(1L), any())).thenReturn(7L);

        // Ação: (7 - 5) * 20 = 40 pontos
        BigDecimal score = rule.check(tx);

        assertEquals(new BigDecimal("40"), score, "Deve retornar 40 pontos para 2 transações acima do limite");
    }

    @Test
    void shouldReturnZeroWhenFrequencyIsNormal() {
        Account account = new Account();
        account.setId(1L);
        Transaction tx = Transaction.builder().account(account).build();

        // Simulamos apenas 3 transações (dentro do limite)
        when(transactionRepository.countTransactionsSince(eq(1L), any())).thenReturn(3L);

        BigDecimal score = rule.check(tx);

        assertEquals(BigDecimal.ZERO, score, "Não deve pontuar se estiver abaixo do limite de 5");
    }
}