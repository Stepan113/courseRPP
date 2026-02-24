package org.course.courserpp.controller;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CashWithdrawalDto;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.service.CashWithdrawalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cash-withdrawals")
@RequiredArgsConstructor
public class CashWithdrawalController {

    private final CashWithdrawalService cashWithdrawalService;

    @PostMapping
    public ResponseEntity<CashWithdrawal> createCashWithdrawal(@RequestBody CashWithdrawalDto dto) {
        return ResponseEntity.ok(cashWithdrawalService.createCashWithdrawal(dto));
    }

    @PutMapping("/{id}/link-application")
    public ResponseEntity<CashWithdrawal> linkToApplication(@PathVariable String id,
                                                             @RequestParam String applicationForCashId) {
        return ResponseEntity.ok(cashWithdrawalService.linkToApplication(id, applicationForCashId));
    }

    @GetMapping("/by-score/{scoreId}")
    public ResponseEntity<List<CashWithdrawal>> getByScore(@PathVariable String scoreId) {
        return ResponseEntity.ok(cashWithdrawalService.getAllByScore(scoreId));
    }
}
