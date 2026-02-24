package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.MoneyTransferRequestDto;
import org.course.courserpp.service.MoneyTransferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/money-transfers")
@RequiredArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("moneyTransfers", service.findAll());
        return "money-transfers/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        service.findById(id).ifPresent(m -> model.addAttribute("moneyTransfer", m));
        return "money-transfers/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("moneyTransfer", new MoneyTransferRequestDto());
        return "money-transfers/form";
    }

    @PostMapping
    public String create(@ModelAttribute MoneyTransferRequestDto dto) {
        service.create(dto);
        return "redirect:/money-transfers";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        service.findById(id).ifPresent(m -> {
            MoneyTransferRequestDto req = new MoneyTransferRequestDto();
            req.setSum(m.getSum());
            model.addAttribute("moneyTransfer", req);
            model.addAttribute("id", id);
        });
        return "money-transfers/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute MoneyTransferRequestDto dto) {
        service.update(id, dto);
        return "redirect:/money-transfers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/money-transfers";
    }
}
