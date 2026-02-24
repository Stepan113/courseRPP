package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.User;
import org.course.courserpp.service.MoneyTransferService;
import org.course.courserpp.service.ScoreService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/money-transfers")
@RequiredArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;
    private final ScoreService scoreService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("moneyTransfers", moneyTransferService.findAll());
        return "money-transfers/list";
    }

    @GetMapping("/new")
    public String newForm(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("moneyTransfer", new MoneyTransfer());
        model.addAttribute("scores", scoreService.findByUser(user));
        return "money-transfers/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute MoneyTransfer moneyTransfer,
                          @RequestParam(required = false) String scoreId) {
        MoneyTransfer saved = moneyTransferService.save(moneyTransfer);
        if (scoreId != null && !scoreId.isEmpty()) {
            moneyTransferService.linkToScore(saved.getId(), scoreId);
        }
        return "redirect:/money-transfers";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, @AuthenticationPrincipal User user, Model model) {
        moneyTransferService.findById(id).ifPresent(mt -> model.addAttribute("moneyTransfer", mt));
        model.addAttribute("scores", scoreService.findByUser(user));
        return "money-transfers/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id, @ModelAttribute MoneyTransfer moneyTransfer) {
        moneyTransfer.setId(id);
        moneyTransferService.save(moneyTransfer);
        return "redirect:/money-transfers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        moneyTransferService.delete(id);
        return "redirect:/money-transfers";
    }
}
