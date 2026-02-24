package org.course.courserpp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.ReplenishmentOperationRequestDto;
import org.course.courserpp.dto.response.ReplenishmentOperationResponseDto;
import org.course.courserpp.service.ReplenishmentOperationService;
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
@RequestMapping("/api/replenishment-operations")
@RequiredArgsConstructor
public class ReplenishmentOperationController {

    private final ReplenishmentOperationService replenishmentOperationService;

    @GetMapping
    public ResponseEntity<List<ReplenishmentOperationResponseDto>> findAll() {
        return ResponseEntity.ok(replenishmentOperationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReplenishmentOperationResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(replenishmentOperationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReplenishmentOperationResponseDto> create(
            @Valid @RequestBody ReplenishmentOperationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(replenishmentOperationService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReplenishmentOperationResponseDto> update(
            @PathVariable String id,
            @Valid @RequestBody ReplenishmentOperationRequestDto dto) {
        return ResponseEntity.ok(replenishmentOperationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        replenishmentOperationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
