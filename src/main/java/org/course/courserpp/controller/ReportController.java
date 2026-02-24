package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CashWithdrawalResponseDto;
import org.course.courserpp.dto.ReplenishmentOperationResponseDto;
import org.course.courserpp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final UserService userService;
    private final CardService cardService;
    private final ScoreService scoreService;
    private final ApplicationForCashService applicationForCashService;
    private final CashWithdrawalService cashWithdrawalService;
    private final MoneyTransferService moneyTransferService;
    private final ReplenishmentOperationService replenishmentOperationService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("userCount", userService.findAll().size());
        model.addAttribute("cardCount", cardService.findAll().size());
        model.addAttribute("scoreCount", scoreService.findAll().size());
        model.addAttribute("applicationCount", applicationForCashService.findAll().size());
        model.addAttribute("withdrawalCount", cashWithdrawalService.findAll().size());
        model.addAttribute("transferCount", moneyTransferService.findAll().size());
        model.addAttribute("replenishmentCount", replenishmentOperationService.findAll().size());

        List<CashWithdrawalResponseDto> withdrawals = cashWithdrawalService.findAll();
        long totalWithdrawals = withdrawals.stream()
                .mapToLong(w -> w.getWithdrawalAmount() != null ? w.getWithdrawalAmount() : 0L)
                .sum();
        model.addAttribute("totalWithdrawals", totalWithdrawals);

        List<ReplenishmentOperationResponseDto> replenishments = replenishmentOperationService.findAll();
        long totalReplenishments = replenishments.stream()
                .mapToLong(r -> r.getSum() != null ? r.getSum() : 0L)
                .sum();
        model.addAttribute("totalReplenishments", totalReplenishments);

        long totalTransfers = moneyTransferService.findAll().stream()
                .mapToLong(t -> t.getSum() != null ? t.getSum() : 0L)
                .sum();
        model.addAttribute("totalTransfers", totalTransfers);

        return "reports/index";
    }
}
