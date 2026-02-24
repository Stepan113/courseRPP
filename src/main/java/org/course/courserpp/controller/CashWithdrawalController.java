package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CashWithdrawalRequestDto;
import org.course.courserpp.service.CashWithdrawalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cash-withdrawals")
@RequiredArgsConstructor
public class CashWithdrawalController {

    private final CashWithdrawalService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("cashWithdrawals", service.findAll());
        return "cash-withdrawals/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        service.findById(id).ifPresent(c -> model.addAttribute("cashWithdrawal", c));
        return "cash-withdrawals/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("cashWithdrawal", new CashWithdrawalRequestDto());
        return "cash-withdrawals/form";
    }

    @PostMapping
    public String create(@ModelAttribute CashWithdrawalRequestDto dto) {
        service.create(dto);
        return "redirect:/cash-withdrawals";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        service.findById(id).ifPresent(c -> {
            CashWithdrawalRequestDto req = new CashWithdrawalRequestDto();
            req.setWithdrawalAmount(c.getWithdrawalAmount());
            model.addAttribute("cashWithdrawal", req);
            model.addAttribute("id", id);
        });
        return "cash-withdrawals/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute CashWithdrawalRequestDto dto) {
        service.update(id, dto);
        return "redirect:/cash-withdrawals";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/cash-withdrawals";
    }
}
