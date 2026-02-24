package org.course.courserpp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.ScoreRequestDto;
import org.course.courserpp.dto.response.ScoreResponseDto;
import org.course.courserpp.service.ScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @GetMapping
    public ResponseEntity<List<ScoreResponseDto>> findAll() {
        return ResponseEntity.ok(scoreService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(scoreService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ScoreResponseDto> create(@Valid @RequestBody ScoreRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScoreResponseDto> update(@PathVariable String id,
                                                    @Valid @RequestBody ScoreRequestDto dto) {
        return ResponseEntity.ok(scoreService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        scoreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
