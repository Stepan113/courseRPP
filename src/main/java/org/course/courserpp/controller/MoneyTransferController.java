package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.MoneyTransferRequestDto;
import org.course.courserpp.service.MoneyTransferService;
import org.course.courserpp.service.ScoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;
    private final ScoreService scoreService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("transfers", moneyTransferService.findAll());
        return "transfers/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("transfer", new MoneyTransferRequestDto());
        model.addAttribute("scores", scoreService.findAll());
        return "transfers/form";
    }

    @PostMapping
    public String create(@ModelAttribute MoneyTransferRequestDto dto) {
        moneyTransferService.save(dto);
        return "redirect:/transfers";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("transfer", moneyTransferService.findById(id));
        model.addAttribute("scores", scoreService.findAll());
        model.addAttribute("id", id);
        return "transfers/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute MoneyTransferRequestDto dto) {
        moneyTransferService.update(id, dto);
        return "redirect:/transfers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        moneyTransferService.delete(id);
        return "redirect:/transfers";
    }
}
