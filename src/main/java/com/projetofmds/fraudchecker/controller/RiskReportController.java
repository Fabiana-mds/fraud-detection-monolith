package com.projetofmds.fraudchecker.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.projetofmds.fraudchecker.model.RiskReport;
import com.projetofmds.fraudchecker.service.RiskReportService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/risk-report")
@RequiredArgsConstructor
public class RiskReportController {

    private final RiskReportService riskReportService;

    @PostMapping("/generate")
    public RiskReport generate() {
        return riskReportService.generateCurrentReport();
    }

    @GetMapping
    public List<RiskReport> getAll() {
        return riskReportService.getAllReports();
    }
}
