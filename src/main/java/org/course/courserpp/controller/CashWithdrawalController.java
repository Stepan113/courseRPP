package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CashWithdrawalRequestDto;
import org.course.courserpp.service.CashWithdrawalService;
import org.course.courserpp.service.ScoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/withdrawals")
@RequiredArgsConstructor
public class CashWithdrawalController {

    private final CashWithdrawalService cashWithdrawalService;
    private final ScoreService scoreService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("withdrawals", cashWithdrawalService.findAll());
        return "withdrawals/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("withdrawal", new CashWithdrawalRequestDto());
        model.addAttribute("scores", scoreService.findAll());
        return "withdrawals/form";
    }

    @PostMapping
    public String create(@ModelAttribute CashWithdrawalRequestDto dto) {
        cashWithdrawalService.save(dto);
        return "redirect:/withdrawals";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("withdrawal", cashWithdrawalService.findById(id));
        model.addAttribute("scores", scoreService.findAll());
        model.addAttribute("id", id);
        return "withdrawals/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute CashWithdrawalRequestDto dto) {
        cashWithdrawalService.update(id, dto);
        return "redirect:/withdrawals";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        cashWithdrawalService.delete(id);
        return "redirect:/withdrawals";
    }
}
