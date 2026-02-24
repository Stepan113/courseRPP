package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/withdrawals/{scoreId}")
    public ResponseEntity<byte[]> getWithdrawalReport(@PathVariable String scoreId,
                                                       @RequestParam(defaultValue = "txt") String format) {
        if ("csv".equalsIgnoreCase(format)) {
            String csv = reportService.generateWithdrawalReportCsv(scoreId);
            return buildFileResponse(csv, "withdrawals_" + scoreId + ".csv", "text/csv");
        }
        String text = reportService.generateWithdrawalReportText(scoreId);
        return buildFileResponse(text, "withdrawals_" + scoreId + ".txt", "text/plain");
    }

    @GetMapping("/transfers/{scoreId}")
    public ResponseEntity<byte[]> getTransferReport(@PathVariable String scoreId,
                                                     @RequestParam(defaultValue = "txt") String format) {
        if ("csv".equalsIgnoreCase(format)) {
            String csv = reportService.generateTransferReportCsv(scoreId);
            return buildFileResponse(csv, "transfers_" + scoreId + ".csv", "text/csv");
        }
        String text = reportService.generateTransferReportText(scoreId);
        return buildFileResponse(text, "transfers_" + scoreId + ".txt", "text/plain");
    }

    @GetMapping("/applications/{scoreId}")
    public ResponseEntity<byte[]> getApplicationsReport(@PathVariable String scoreId,
                                                         @RequestParam(defaultValue = "txt") String format) {
        if ("csv".equalsIgnoreCase(format)) {
            String csv = reportService.generateApplicationsReportCsv(scoreId);
            return buildFileResponse(csv, "applications_" + scoreId + ".csv", "text/csv");
        }
        String text = reportService.generateApplicationsReportText(scoreId);
        return buildFileResponse(text, "applications_" + scoreId + ".txt", "text/plain");
    }

    @GetMapping("/statement/{scoreId}")
    public ResponseEntity<byte[]> getAccountStatement(
            @PathVariable String scoreId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to) {
        String text = reportService.generateAccountStatementText(scoreId, from, to);
        return buildFileResponse(text, "statement_" + scoreId + ".txt", "text/plain");
    }

    @PostMapping("/statement/{scoreId}/email")
    public ResponseEntity<String> sendAccountStatementByEmail(
            @PathVariable String scoreId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
            @RequestParam String email) {
        reportService.sendAccountStatementByEmail(scoreId, from, to, email);
        return ResponseEntity.ok("Выписка отправлена на " + email);
    }

    private ResponseEntity<byte[]> buildFileResponse(String content, String filename, String mediaType) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mediaType + ";charset=UTF-8"));
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename, StandardCharsets.UTF_8).build());
        headers.setContentLength(bytes.length);
        return ResponseEntity.ok().headers(headers).body(bytes);
    }
}
