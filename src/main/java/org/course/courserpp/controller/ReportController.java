package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public String index() {
        return "reports/index";
    }

    @GetMapping("/cash-withdrawals")
    public String cashWithdrawals(Model model) {
        model.addAttribute("cashWithdrawals", reportService.getCashWithdrawalsReport());
        return "reports/cash-withdrawals";
    }

    @GetMapping("/money-transfers")
    public String moneyTransfers(Model model) {
        model.addAttribute("moneyTransfers", reportService.getMoneyTransfersReport());
        return "reports/money-transfers";
    }

    @GetMapping("/replenishment-operations")
    public String replenishmentOperations(Model model) {
        model.addAttribute("replenishmentOperations", reportService.getReplenishmentOperationsReport());
        return "reports/replenishment-operations";
    }
}
