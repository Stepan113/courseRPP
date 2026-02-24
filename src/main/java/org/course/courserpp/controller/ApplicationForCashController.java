package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ApplicationForCashRequestDto;
import org.course.courserpp.service.ApplicationForCashService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/applications-for-cash")
@RequiredArgsConstructor
public class ApplicationForCashController {

    private final ApplicationForCashService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("applications", service.findAll());
        return "applications-for-cash/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        service.findById(id).ifPresent(a -> model.addAttribute("application", a));
        return "applications-for-cash/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("application", new ApplicationForCashRequestDto());
        return "applications-for-cash/form";
    }

    @PostMapping
    public String create(@ModelAttribute ApplicationForCashRequestDto dto) {
        service.create(dto);
        return "redirect:/applications-for-cash";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        service.findById(id).ifPresent(a -> {
            ApplicationForCashRequestDto req = new ApplicationForCashRequestDto();
            req.setDelay(a.getDelay());
            req.setWithdrawalAmount(a.getWithdrawalAmount());
            model.addAttribute("application", req);
            model.addAttribute("id", id);
        });
        return "applications-for-cash/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute ApplicationForCashRequestDto dto) {
        service.update(id, dto);
        return "redirect:/applications-for-cash";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/applications-for-cash";
    }
}
