package com.projetofmds.fraudchecker.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "risk_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime generatedAt;
    private Long totalTransactions;
    private Long rejectedTransactions;
    private BigDecimal totalAmountProcessed;
}

