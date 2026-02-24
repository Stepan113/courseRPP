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

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        cardService.findById(id).ifPresent(c -> model.addAttribute("card", c));
        return "cards/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("card", new CardRequestDto());
        model.addAttribute("users", userService.findAll());
        return "cards/form";
    }

    @PostMapping
    public String create(@ModelAttribute CardRequestDto dto) {
        cardService.create(dto);
        return "redirect:/cards";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        cardService.findById(id).ifPresent(c -> {
            CardRequestDto req = new CardRequestDto();
            req.setNumber(c.getNumber());
            req.setBalance(c.getBalance());
            req.setCvc(c.getCvc());
            req.setUserId(c.getUserId());
            model.addAttribute("card", req);
            model.addAttribute("id", id);
        });
        model.addAttribute("users", userService.findAll());
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
