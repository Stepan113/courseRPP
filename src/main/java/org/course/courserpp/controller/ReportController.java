package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.Score;
import org.course.courserpp.model.User;
import org.course.courserpp.service.EmailService;
import org.course.courserpp.service.ReportService;
import org.course.courserpp.service.ScoreService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final ScoreService scoreService;
    private final EmailService emailService;

    @GetMapping
    public String reportPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("scores", scoreService.findByUser(user));
        return "reports/index";
    }

    @GetMapping("/text")
    @ResponseBody
    public ResponseEntity<String> textReport(@AuthenticationPrincipal User user,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        List<Score> scores = scoreService.findByUser(user);
        String report = reportService.generateTextReport(scores, from, to);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.txt\"")
                .body(report);
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> excelReport(@AuthenticationPrincipal User user,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) throws IOException {
        List<Score> scores = scoreService.findByUser(user);
        byte[] data = reportService.generateExcelReport(scores, from, to);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.xlsx\"")
                .body(data);
    }

    @PostMapping("/email")
    public String emailReport(@AuthenticationPrincipal User user,
                               @RequestParam String email,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                               Model model) {
        List<Score> scores = scoreService.findByUser(user);
        String report = reportService.generateTextReport(scores, from, to);
        try {
            emailService.sendReport(email, "Account Report", report);
            model.addAttribute("success", "Report sent to " + email);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to send email: " + e.getMessage());
        }
        model.addAttribute("scores", scores);
        return "reports/index";
    }
}
