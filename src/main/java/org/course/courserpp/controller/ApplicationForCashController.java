package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.service.ApplicationForCashService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationForCashController {

    private final ApplicationForCashService applicationForCashService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("applications", applicationForCashService.findAll());
        return "applications/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("application", new ApplicationForCash());
        return "applications/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute ApplicationForCash application) {
        applicationForCashService.save(application);
        return "redirect:/applications";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        applicationForCashService.findById(id).ifPresent(a -> model.addAttribute("application", a));
        return "applications/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id, @ModelAttribute ApplicationForCash application) {
        application.setId(id);
        applicationForCashService.save(application);
        return "redirect:/applications";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        applicationForCashService.delete(id);
        return "redirect:/applications";
    }
}
