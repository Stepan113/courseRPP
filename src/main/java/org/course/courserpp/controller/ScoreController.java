package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.Score;
import org.course.courserpp.model.User;
import org.course.courserpp.service.ScoreService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @GetMapping
    public String list(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("scores", scoreService.findByUser(user));
        return "scores/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("score", new Score());
        return "scores/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Score score, @AuthenticationPrincipal User user) {
        score.setUser(user);
        scoreService.save(score);
        return "redirect:/scores";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        scoreService.findById(id).ifPresent(s -> model.addAttribute("score", s));
        return "scores/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id, @ModelAttribute Score score, @AuthenticationPrincipal User user) {
        score.setId(id);
        score.setUser(user);
        scoreService.save(score);
        return "redirect:/scores";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        scoreService.delete(id);
        return "redirect:/scores";
    }
}
