package com.projetofmds.fraudchecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.projetofmds.fraudchecker.model.RiskReport;

@Repository
public interface RiskReportRepository extends JpaRepository<RiskReport, Long> {
}
