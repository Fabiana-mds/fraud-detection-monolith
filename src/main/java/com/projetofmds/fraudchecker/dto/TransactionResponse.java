package com.projetofmds.fraudchecker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
    Long Id,
    BigDecimal amount,
    LocalDateTime timestamp,
    String status,
    BigDecimal riskScore
) {}
