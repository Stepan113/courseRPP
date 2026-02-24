package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ScoreDto;
import org.course.courserpp.model.Score;
import org.course.courserpp.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping
    public ResponseEntity<Score> createScore(@RequestBody ScoreDto dto) {
        return ResponseEntity.ok(scoreService.createScore(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Score>> getScoresByUser(@PathVariable String userId) {
        return ResponseEntity.ok(scoreService.getScoresByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Score> getScore(@PathVariable String id) {
        return ResponseEntity.ok(scoreService.getById(id));
    }
}
