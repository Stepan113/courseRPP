package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.CashWithdrawalRequestDto;
import org.course.courserpp.dto.response.CashWithdrawalResponseDto;
import org.course.courserpp.mapper.CashWithdrawalMapper;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.CashWithdrawalRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashWithdrawalService {

    private final CashWithdrawalRepository cashWithdrawalRepository;
    private final ScoreRepository scoreRepository;
    private final CashWithdrawalMapper cashWithdrawalMapper;

    @Transactional(readOnly = true)
    public List<CashWithdrawalResponseDto> findAll() {
        return cashWithdrawalRepository.findAll().stream()
                .map(cashWithdrawalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CashWithdrawalResponseDto findById(String id) {
        CashWithdrawal entity = cashWithdrawalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("CashWithdrawal not found with id: " + id));
        return cashWithdrawalMapper.toResponse(entity);
    }

    @Transactional
    public CashWithdrawalResponseDto create(CashWithdrawalRequestDto dto) {
        Score score = scoreRepository.findById(dto.getScoreId())
                .orElseThrow(() -> new NoSuchElementException("Score not found with id: " + dto.getScoreId()));
        CashWithdrawal entity = cashWithdrawalMapper.toEntity(dto);
        entity = cashWithdrawalRepository.save(entity);
        score.getCashWithdrawals().add(entity);
        scoreRepository.save(score);
        return cashWithdrawalMapper.toResponse(entity);
    }

    @Transactional
    public CashWithdrawalResponseDto update(String id, CashWithdrawalRequestDto dto) {
        CashWithdrawal entity = cashWithdrawalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("CashWithdrawal not found with id: " + id));
        entity.setWithdrawalAmount(dto.getWithdrawalAmount());
        return cashWithdrawalMapper.toResponse(cashWithdrawalRepository.save(entity));
    }

    @Transactional
    public void delete(String id) {
        if (!cashWithdrawalRepository.existsById(id)) {
            throw new NoSuchElementException("CashWithdrawal not found with id: " + id);
        }
        cashWithdrawalRepository.deleteById(id);
    }
}
