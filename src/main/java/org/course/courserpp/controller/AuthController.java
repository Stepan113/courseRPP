package org.course.courserpp.controller;

import jakarta.servlet.http.HttpSession;
import org.course.courserpp.model.User;
import org.course.courserpp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        if (userService.authenticate(login, password)) {
            Optional<User> user = userService.findByLogin(login);
            user.ifPresent(u -> {
                session.setAttribute("userId", u.getId());
                session.setAttribute("userLogin", u.getLogin());
            });
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Неверный логин или пароль");
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String login,
                           @RequestParam String password,
                           Model model) {
        boolean success = userService.register(login, password);
        if (success) {
            return "redirect:/login";
        }
        model.addAttribute("error", "Пользователь с таким логином уже существует");
        return "auth/register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
