package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CardRequestDto;
import org.course.courserpp.service.CardService;
import org.course.courserpp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final UserService userService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("cards", cardService.findAll());
        return "cards/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("card", new CardRequestDto());
        model.addAttribute("users", userService.findAll());
        return "cards/form";
    }

    @PostMapping
    public String create(@ModelAttribute CardRequestDto dto) {
        cardService.save(dto);
        return "redirect:/cards";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("card", cardService.findById(id));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("id", id);
        return "cards/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute CardRequestDto dto) {
        cardService.update(id, dto);
        return "redirect:/cards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        cardService.delete(id);
        return "redirect:/cards";
    }
}
