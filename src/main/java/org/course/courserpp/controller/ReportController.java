package org.course.courserpp.controller;

import jakarta.servlet.http.HttpSession;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.Score;
import org.course.courserpp.service.CardService;
import org.course.courserpp.service.EmailService;
import org.course.courserpp.service.ReportService;
import org.course.courserpp.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String reportPage(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        model.addAttribute("scores", scoreService.findByUserId(userId));
        return "report/list";
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> downloadExcel(@RequestParam(required = false) List<String> scoreIds,
                                                HttpSession session) throws IOException {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();

        if (scoreIds != null && !scoreIds.isEmpty()) {
            List<Score> selectedScores = collectSelectedScores(userId, scoreIds);
            byte[] bytes = reportService.generateExcelForScores(selectedScores);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=otchet_po_schetam.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(bytes);
        }

        List<ApplicationForCash> apps = collectApplications(userId);
        byte[] bytes = reportService.generateExcel(apps);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=zayavki.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }

    @GetMapping("/word")
    public ResponseEntity<byte[]> downloadWord(@RequestParam(required = false) List<String> scoreIds,
                                               HttpSession session) throws IOException {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(302).header(HttpHeaders.LOCATION, "/login").build();

        if (scoreIds != null && !scoreIds.isEmpty()) {
            List<Score> selectedScores = collectSelectedScores(userId, scoreIds);
            byte[] bytes = reportService.generateWordForScores(selectedScores);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=otchet_po_schetam.docx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                    .body(bytes);
        }

        List<ApplicationForCash> apps = collectApplications(userId);
        byte[] bytes = reportService.generateWord(apps);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=zayavki.docx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .body(bytes);
    }

    @PostMapping("/email")
    public String sendEmailReport(@RequestParam String email,
                                  HttpSession session,
                                  Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        List<Score> allScores = scoreService.findByUserId(userId);

        try {
            byte[] pdf = reportService.generatePdfForAllScores(allScores);
            emailService.sendPdfReport(email, pdf, "Отчёт по всем счетам");
            model.addAttribute("success", "Отчёт отправлен на " + email);
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка отправки: " + e.getMessage());
        }

        model.addAttribute("scores", allScores);
        return "report/list";
    }

    private List<ApplicationForCash> collectApplications(String userId) {
        List<Card> cards = cardService.findByUserId(userId);
        List<ApplicationForCash> apps = new ArrayList<>();
        for (Card card : cards) {
            apps.addAll(card.getApplicationForCashes());
        }
        return apps;
    }

    private List<Score> collectSelectedScores(String userId, List<String> scoreIds) {
        List<Score> result = new ArrayList<>();
        for (String scoreId : scoreIds) {
            Optional<Score> scoreOpt = scoreService.findById(scoreId);
            if (scoreOpt.isPresent()) {
                Score score = scoreOpt.get();
                if (score.getUser() != null && userId.equals(score.getUser().getId())) {
                    result.add(score);
                }
            }
        }
        return result;
    }
}
