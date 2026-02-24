package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.UserRequestDto;
import org.course.courserpp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model) {
        userService.findById(id).ifPresent(u -> model.addAttribute("user", u));
        return "users/view";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "users/form";
    }

    @PostMapping
    public String create(@ModelAttribute UserRequestDto dto) {
        userService.create(dto);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        userService.findById(id).ifPresent(u -> {
            UserRequestDto req = new UserRequestDto();
            req.setLogin(u.getLogin());
            model.addAttribute("user", req);
            model.addAttribute("id", id);
        });
        return "users/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute UserRequestDto dto) {
        userService.update(id, dto);
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
