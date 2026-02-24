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

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        scoreService.findById(id).ifPresent(s -> model.addAttribute("score", s));
        return "scores/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("score", new ScoreRequestDto());
        model.addAttribute("users", userService.findAll());
        return "scores/form";
    }

    @PostMapping
    public String create(@ModelAttribute ScoreRequestDto dto) {
        scoreService.create(dto);
        return "redirect:/scores";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        scoreService.findById(id).ifPresent(s -> {
            ScoreRequestDto req = new ScoreRequestDto();
            req.setNumber(s.getNumber());
            req.setBalance(s.getBalance());
            req.setCurrency(s.getCurrency());
            req.setUserId(s.getUserId());
            model.addAttribute("score", req);
            model.addAttribute("id", id);
        });
        model.addAttribute("users", userService.findAll());
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
