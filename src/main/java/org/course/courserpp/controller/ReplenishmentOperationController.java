package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.ReplenishmentOperation;
import org.course.courserpp.service.ReplenishmentOperationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/replenishments")
@RequiredArgsConstructor
public class ReplenishmentOperationController {

    private final ReplenishmentOperationService replenishmentOperationService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("replenishments", replenishmentOperationService.findAll());
        return "replenishments/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("replenishment", new ReplenishmentOperation());
        return "replenishments/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute ReplenishmentOperation replenishment) {
        replenishmentOperationService.save(replenishment);
        return "redirect:/replenishments";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        replenishmentOperationService.findById(id).ifPresent(r -> model.addAttribute("replenishment", r));
        return "replenishments/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id, @ModelAttribute ReplenishmentOperation replenishment) {
        replenishment.setId(id);
        replenishmentOperationService.save(replenishment);
        return "redirect:/replenishments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        replenishmentOperationService.delete(id);
        return "redirect:/replenishments";
    }
}
