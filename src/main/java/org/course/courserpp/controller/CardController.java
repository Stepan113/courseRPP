package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.User;
import org.course.courserpp.service.CardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping
    public String list(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("cards", cardService.findByUser(user));
        return "cards/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("card", new Card());
        return "cards/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Card card, @AuthenticationPrincipal User user) {
        card.setUser(user);
        cardService.save(card);
        return "redirect:/cards";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        cardService.findById(id).ifPresent(c -> model.addAttribute("card", c));
        return "cards/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id, @ModelAttribute Card card, @AuthenticationPrincipal User user) {
        card.setId(id);
        card.setUser(user);
        cardService.save(card);
        return "redirect:/cards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        cardService.delete(id);
        return "redirect:/cards";
    }
}
