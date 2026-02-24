package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ApplicationForCashDto;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.service.ApplicationForCashService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationForCashController {

    private final ApplicationForCashService applicationForCashService;

    @PostMapping
    public ResponseEntity<ApplicationForCash> createApplication(@RequestBody ApplicationForCashDto dto) {
        return ResponseEntity.ok(applicationForCashService.createApplication(dto));
    }

    @GetMapping("/by-score/{scoreId}")
    public ResponseEntity<List<ApplicationForCash>> getByScore(@PathVariable String scoreId) {
        return ResponseEntity.ok(applicationForCashService.getAllByScore(scoreId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationForCash> getById(@PathVariable String id) {
        return ResponseEntity.ok(applicationForCashService.getById(id));
    }
}
