package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.MoneyTransferRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoneyTransferService {

    private final MoneyTransferRepository moneyTransferRepository;
    private final ScoreRepository scoreRepository;

    public List<MoneyTransfer> findAll() {
        return moneyTransferRepository.findAll();
    }

    public Optional<MoneyTransfer> findById(String id) {
        return moneyTransferRepository.findById(id);
    }

    public MoneyTransfer save(MoneyTransfer moneyTransfer) {
        return moneyTransferRepository.save(moneyTransfer);
    }

    public void delete(String id) {
        moneyTransferRepository.deleteById(id);
    }

    @Transactional
    public MoneyTransfer linkToScore(String moneyTransferId, String scoreId) {
        MoneyTransfer mt = moneyTransferRepository.findById(moneyTransferId)
                .orElseThrow(() -> new IllegalArgumentException("MoneyTransfer not found"));
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found"));
        score.getMoneyTransfers().add(mt);
        scoreRepository.save(score);
        return mt;
    }
}
