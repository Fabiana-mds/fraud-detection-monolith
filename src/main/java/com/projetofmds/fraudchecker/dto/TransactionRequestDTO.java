package com.projetofmds.fraudchecker.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TransactionRequestDTO(
    @NotNull Long accountId,
    @NotNull @Min(1) BigDecimal amount,
    @NotNull String typeString
) {}
