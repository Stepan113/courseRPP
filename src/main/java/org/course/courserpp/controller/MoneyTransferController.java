package org.course.courserpp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.MoneyTransferRequestDto;
import org.course.courserpp.dto.response.MoneyTransferResponseDto;
import org.course.courserpp.service.MoneyTransferService;
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
@RequestMapping("/api/money-transfers")
@RequiredArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;

    @GetMapping
    public ResponseEntity<List<MoneyTransferResponseDto>> findAll() {
        return ResponseEntity.ok(moneyTransferService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoneyTransferResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(moneyTransferService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MoneyTransferResponseDto> create(@Valid @RequestBody MoneyTransferRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moneyTransferService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoneyTransferResponseDto> update(@PathVariable String id,
                                                            @Valid @RequestBody MoneyTransferRequestDto dto) {
        return ResponseEntity.ok(moneyTransferService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        moneyTransferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
