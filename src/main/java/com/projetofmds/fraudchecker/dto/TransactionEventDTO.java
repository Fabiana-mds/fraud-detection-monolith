package com.projetofmds.fraudchecker.dto;

import java.math.BigDecimal;

public record TransactionEventDTO(
    Long transactionId,
    Long accountId,
    BigDecimal amount
) {}
