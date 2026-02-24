package org.course.courserpp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.CashWithdrawalRequestDto;
import org.course.courserpp.dto.response.CashWithdrawalResponseDto;
import org.course.courserpp.service.CashWithdrawalService;
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
@RequestMapping("/api/cash-withdrawals")
@RequiredArgsConstructor
public class CashWithdrawalController {

    private final CashWithdrawalService cashWithdrawalService;

    @GetMapping
    public ResponseEntity<List<CashWithdrawalResponseDto>> findAll() {
        return ResponseEntity.ok(cashWithdrawalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashWithdrawalResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(cashWithdrawalService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CashWithdrawalResponseDto> create(@Valid @RequestBody CashWithdrawalRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cashWithdrawalService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CashWithdrawalResponseDto> update(@PathVariable String id,
                                                             @Valid @RequestBody CashWithdrawalRequestDto dto) {
        return ResponseEntity.ok(cashWithdrawalService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        cashWithdrawalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
