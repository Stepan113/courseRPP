package org.course.courserpp.controller;

import jakarta.servlet.http.HttpSession;
import org.course.courserpp.model.Score;
import org.course.courserpp.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping
    public String list(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        model.addAttribute("scores", scoreService.findByUserId(userId));
        return "score/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable String id, HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Optional<Score> scoreOpt = scoreService.findById(id);
        if (scoreOpt.isEmpty()) return "redirect:/scores";

        Score score = scoreOpt.get();
        // Verify score belongs to the logged-in user
        if (score.getUser() == null || !userId.equals(score.getUser().getId())) {
            return "redirect:/scores";
        }
        model.addAttribute("score", score);
        model.addAttribute("cashWithdrawals", score.getCashWithdrawals());
        model.addAttribute("moneyTransfers", score.getMoneyTransfers());
        return "score/detail";
    }
}
