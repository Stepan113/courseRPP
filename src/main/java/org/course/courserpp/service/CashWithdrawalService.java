package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.CashWithdrawalRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashWithdrawalService {

    private final CashWithdrawalRepository cashWithdrawalRepository;
    private final ScoreRepository scoreRepository;

    public List<CashWithdrawal> findAll() {
        return cashWithdrawalRepository.findAll();
    }

    public Optional<CashWithdrawal> findById(String id) {
        return cashWithdrawalRepository.findById(id);
    }

    public CashWithdrawal save(CashWithdrawal cashWithdrawal) {
        return cashWithdrawalRepository.save(cashWithdrawal);
    }

    public void delete(String id) {
        cashWithdrawalRepository.deleteById(id);
    }

    @Transactional
    public CashWithdrawal linkToScore(String cashWithdrawalId, String scoreId) {
        CashWithdrawal cw = cashWithdrawalRepository.findById(cashWithdrawalId)
                .orElseThrow(() -> new IllegalArgumentException("CashWithdrawal not found"));
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found"));
        score.getCashWithdrawals().add(cw);
        scoreRepository.save(score);
        return cw;
    }

    @Transactional
    public CashWithdrawal linkToApplication(String cashWithdrawalId, ApplicationForCash application) {
        CashWithdrawal cw = cashWithdrawalRepository.findById(cashWithdrawalId)
                .orElseThrow(() -> new IllegalArgumentException("CashWithdrawal not found"));
        cw.setApplicationForCash(application);
        return cashWithdrawalRepository.save(cw);
    }
}
