package org.course.courserpp.controller;

import jakarta.servlet.http.HttpSession;
import org.course.courserpp.service.CardService;
import org.course.courserpp.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ScoreService scoreService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        String userLogin = (String) session.getAttribute("userLogin");
        model.addAttribute("userLogin", userLogin);
        model.addAttribute("cards", cardService.findByUserId(userId));
        model.addAttribute("scores", scoreService.findByUserId(userId));
        return "dashboard/index";
    }
}
