package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.MoneyTransferDto;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.service.MoneyTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/money-transfers")
@RequiredArgsConstructor
public class MoneyTransferController {

    private final MoneyTransferService moneyTransferService;

    @PostMapping
    public ResponseEntity<MoneyTransfer> createMoneyTransfer(@RequestBody MoneyTransferDto dto) {
        return ResponseEntity.ok(moneyTransferService.createMoneyTransfer(dto));
    }

    @GetMapping("/by-score/{scoreId}")
    public ResponseEntity<List<MoneyTransfer>> getByScore(@PathVariable String scoreId) {
        return ResponseEntity.ok(moneyTransferService.getAllByScore(scoreId));
    }
}
