package com.projetofmds.fraudchecker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
    Long Id,
    BigDecimal amount,
    LocalDateTime timestamp,
    String status,
    BigDecimal riskScore
) {}
