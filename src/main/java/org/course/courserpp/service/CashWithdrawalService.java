package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CashWithdrawalDto;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.ApplicationForCashRepository;
import org.course.courserpp.repository.CashWithdrawalRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CashWithdrawalService {

    private final CashWithdrawalRepository cashWithdrawalRepository;
    private final ScoreRepository scoreRepository;
    private final ApplicationForCashRepository applicationForCashRepository;

    @Transactional
    public CashWithdrawal createCashWithdrawal(CashWithdrawalDto dto) {
        CashWithdrawal cashWithdrawal = new CashWithdrawal();
        cashWithdrawal.setWithdrawalAmount(dto.getWithdrawalAmount());

        if (dto.getApplicationForCashId() != null) {
            ApplicationForCash application = applicationForCashRepository.findById(dto.getApplicationForCashId())
                    .orElseThrow(() -> new IllegalArgumentException("ApplicationForCash not found: " + dto.getApplicationForCashId()));
            cashWithdrawal.setApplicationForCash(application);
        }

        CashWithdrawal saved = cashWithdrawalRepository.save(cashWithdrawal);

        if (dto.getScoreIds() != null) {
            List<Score> scores = scoreRepository.findAllById(dto.getScoreIds());
            for (Score score : scores) {
                score.getCashWithdrawals().add(saved);
            }
            scoreRepository.saveAll(scores);
        }

        return saved;
    }

    @Transactional
    public CashWithdrawal linkToApplication(String cashWithdrawalId, String applicationForCashId) {
        CashWithdrawal cashWithdrawal = cashWithdrawalRepository.findById(cashWithdrawalId)
                .orElseThrow(() -> new IllegalArgumentException("CashWithdrawal not found: " + cashWithdrawalId));
        ApplicationForCash application = applicationForCashRepository.findById(applicationForCashId)
                .orElseThrow(() -> new IllegalArgumentException("ApplicationForCash not found: " + applicationForCashId));
        cashWithdrawal.setApplicationForCash(application);
        return cashWithdrawalRepository.save(cashWithdrawal);
    }

    public List<CashWithdrawal> getAllByScore(String scoreId) {
        return cashWithdrawalRepository.findAllByScoreId(scoreId);
    }
}
