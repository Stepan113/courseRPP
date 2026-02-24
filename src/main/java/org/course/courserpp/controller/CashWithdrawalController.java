package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.User;
import org.course.courserpp.service.ApplicationForCashService;
import org.course.courserpp.service.CashWithdrawalService;
import org.course.courserpp.service.ScoreService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cash-withdrawals")
@RequiredArgsConstructor
public class CashWithdrawalController {

    private final CashWithdrawalService cashWithdrawalService;
    private final ScoreService scoreService;
    private final ApplicationForCashService applicationForCashService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("cashWithdrawals", cashWithdrawalService.findAll());
        return "cash-withdrawals/list";
    }

    @GetMapping("/new")
    public String newForm(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("cashWithdrawal", new CashWithdrawal());
        model.addAttribute("scores", scoreService.findByUser(user));
        model.addAttribute("applications", applicationForCashService.findAll());
        return "cash-withdrawals/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute CashWithdrawal cashWithdrawal,
                          @RequestParam(required = false) String scoreId,
                          @RequestParam(required = false) String applicationId) {
        CashWithdrawal saved = cashWithdrawalService.save(cashWithdrawal);
        if (scoreId != null && !scoreId.isEmpty()) {
            cashWithdrawalService.linkToScore(saved.getId(), scoreId);
        }
        if (applicationId != null && !applicationId.isEmpty()) {
            applicationForCashService.findById(applicationId)
                    .ifPresent(app -> cashWithdrawalService.linkToApplication(saved.getId(), app));
        }
        return "redirect:/cash-withdrawals";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, @AuthenticationPrincipal User user, Model model) {
        cashWithdrawalService.findById(id).ifPresent(cw -> model.addAttribute("cashWithdrawal", cw));
        model.addAttribute("scores", scoreService.findByUser(user));
        model.addAttribute("applications", applicationForCashService.findAll());
        return "cash-withdrawals/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id, @ModelAttribute CashWithdrawal cashWithdrawal,
                          @RequestParam(required = false) String applicationId) {
        cashWithdrawal.setId(id);
        if (applicationId != null && !applicationId.isEmpty()) {
            applicationForCashService.findById(applicationId)
                    .ifPresent(cashWithdrawal::setApplicationForCash);
        }
        cashWithdrawalService.save(cashWithdrawal);
        return "redirect:/cash-withdrawals";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        cashWithdrawalService.delete(id);
        return "redirect:/cash-withdrawals";
    }
}
