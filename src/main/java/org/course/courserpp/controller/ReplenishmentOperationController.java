package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ReplenishmentOperationRequestDto;
import org.course.courserpp.service.ReplenishmentOperationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/replenishment-operations")
@RequiredArgsConstructor
public class ReplenishmentOperationController {

    private final ReplenishmentOperationService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("replenishmentOperations", service.findAll());
        return "replenishment-operations/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        service.findById(id).ifPresent(r -> model.addAttribute("replenishmentOperation", r));
        return "replenishment-operations/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("replenishmentOperation", new ReplenishmentOperationRequestDto());
        return "replenishment-operations/form";
    }

    @PostMapping
    public String create(@ModelAttribute ReplenishmentOperationRequestDto dto) {
        service.create(dto);
        return "redirect:/replenishment-operations";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        service.findById(id).ifPresent(r -> {
            ReplenishmentOperationRequestDto req = new ReplenishmentOperationRequestDto();
            req.setSum(r.getSum());
            model.addAttribute("replenishmentOperation", req);
            model.addAttribute("id", id);
        });
        return "replenishment-operations/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute ReplenishmentOperationRequestDto dto) {
        service.update(id, dto);
        return "redirect:/replenishment-operations";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/replenishment-operations";
    }
}
