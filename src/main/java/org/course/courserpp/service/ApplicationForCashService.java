package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ApplicationForCashDto;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.repository.ApplicationForCashRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationForCashService {

    private final ApplicationForCashRepository applicationForCashRepository;

    @Transactional
    public ApplicationForCash createApplication(ApplicationForCashDto dto) {
        ApplicationForCash application = new ApplicationForCash();
        application.setDelay(dto.getDelay());
        application.setWithdrawalAmount(dto.getWithdrawalAmount());
        return applicationForCashRepository.save(application);
    }

    public List<ApplicationForCash> getAllByScore(String scoreId) {
        return applicationForCashRepository.findAllByScoreId(scoreId);
    }

    public ApplicationForCash getById(String id) {
        return applicationForCashRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ApplicationForCash not found: " + id));
    }
}
