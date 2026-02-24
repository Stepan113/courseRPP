package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ScoreRequestDto;
import org.course.courserpp.service.ScoreService;
import org.course.courserpp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;
    private final UserService userService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("scores", scoreService.findAll());
        return "scores/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("score", new ScoreRequestDto());
        model.addAttribute("users", userService.findAll());
        return "scores/form";
    }

    @PostMapping
    public String create(@ModelAttribute ScoreRequestDto dto) {
        scoreService.save(dto);
        return "redirect:/scores";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("score", scoreService.findById(id));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("id", id);
        return "scores/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute ScoreRequestDto dto) {
        scoreService.update(id, dto);
        return "redirect:/scores";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        scoreService.delete(id);
        return "redirect:/scores";
    }
}
