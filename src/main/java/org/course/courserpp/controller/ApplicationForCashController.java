package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ApplicationForCashRequestDto;
import org.course.courserpp.service.ApplicationForCashService;
import org.course.courserpp.service.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationForCashController {

    private final ApplicationForCashService applicationForCashService;
    private final CardService cardService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("applications", applicationForCashService.findAll());
        return "applications/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("application", new ApplicationForCashRequestDto());
        model.addAttribute("cards", cardService.findAll());
        return "applications/form";
    }

    @PostMapping
    public String create(@ModelAttribute ApplicationForCashRequestDto dto) {
        applicationForCashService.save(dto);
        return "redirect:/applications";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("application", applicationForCashService.findById(id));
        model.addAttribute("cards", cardService.findAll());
        model.addAttribute("id", id);
        return "applications/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute ApplicationForCashRequestDto dto) {
        applicationForCashService.update(id, dto);
        return "redirect:/applications";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        applicationForCashService.delete(id);
        return "redirect:/applications";
    }
}
