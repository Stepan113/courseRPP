package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.MoneyTransferDto;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.MoneyTransferRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoneyTransferService {

    private final MoneyTransferRepository moneyTransferRepository;
    private final ScoreRepository scoreRepository;

    @Transactional
    public MoneyTransfer createMoneyTransfer(MoneyTransferDto dto) {
        MoneyTransfer moneyTransfer = new MoneyTransfer();
        moneyTransfer.setSum(dto.getSum());

        MoneyTransfer saved = moneyTransferRepository.save(moneyTransfer);

        if (dto.getScoreIds() != null) {
            List<Score> scores = scoreRepository.findAllById(dto.getScoreIds());
            for (Score score : scores) {
                score.getMoneyTransfers().add(saved);
            }
            scoreRepository.saveAll(scores);
        }

        return saved;
    }

    public List<MoneyTransfer> getAllByScore(String scoreId) {
        return moneyTransferRepository.findAllByScoreId(scoreId);
    }
}
