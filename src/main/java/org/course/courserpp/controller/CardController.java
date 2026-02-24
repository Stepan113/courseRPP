package org.course.courserpp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.CardRequestDto;
import org.course.courserpp.dto.response.CardResponseDto;
import org.course.courserpp.service.CardService;
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
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping
    public ResponseEntity<List<CardResponseDto>> findAll() {
        return ResponseEntity.ok(cardService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(cardService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CardResponseDto> create(@Valid @RequestBody CardRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponseDto> update(@PathVariable String id,
                                                   @Valid @RequestBody CardRequestDto dto) {
        return ResponseEntity.ok(cardService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
