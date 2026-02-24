package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ReplenishmentOperationRequestDto;
import org.course.courserpp.service.CardService;
import org.course.courserpp.service.ReplenishmentOperationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/replenishments")
@RequiredArgsConstructor
public class ReplenishmentOperationController {

    private final ReplenishmentOperationService replenishmentOperationService;
    private final CardService cardService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("replenishments", replenishmentOperationService.findAll());
        return "replenishments/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("replenishment", new ReplenishmentOperationRequestDto());
        model.addAttribute("cards", cardService.findAll());
        return "replenishments/form";
    }

    @PostMapping
    public String create(@ModelAttribute ReplenishmentOperationRequestDto dto) {
        replenishmentOperationService.save(dto);
        return "redirect:/replenishments";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("replenishment", replenishmentOperationService.findById(id));
        model.addAttribute("cards", cardService.findAll());
        model.addAttribute("id", id);
        return "replenishments/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute ReplenishmentOperationRequestDto dto) {
        replenishmentOperationService.update(id, dto);
        return "redirect:/replenishments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        replenishmentOperationService.delete(id);
        return "redirect:/replenishments";
    }
}
