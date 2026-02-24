package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CashWithdrawalRequestDto;
import org.course.courserpp.dto.CashWithdrawalResponseDto;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.CashWithdrawalRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashWithdrawalService {

    private final CashWithdrawalRepository cashWithdrawalRepository;
    private final ScoreRepository scoreRepository;

    public List<CashWithdrawalResponseDto> findAll() {
        return cashWithdrawalRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public CashWithdrawalResponseDto findById(String id) {
        return cashWithdrawalRepository.findById(id).map(this::toDto).orElseThrow();
    }

    public CashWithdrawalResponseDto save(CashWithdrawalRequestDto dto) {
        CashWithdrawal entity = new CashWithdrawal();
        entity.setWithdrawalAmount(dto.getWithdrawalAmount());
        CashWithdrawal saved = cashWithdrawalRepository.save(entity);
        Score score = scoreRepository.findById(dto.getScoreId()).orElseThrow();
        score.getCashWithdrawals().add(saved);
        scoreRepository.save(score);
        return toDto(saved);
    }

    public CashWithdrawalResponseDto update(String id, CashWithdrawalRequestDto dto) {
        CashWithdrawal entity = cashWithdrawalRepository.findById(id).orElseThrow();
        entity.setWithdrawalAmount(dto.getWithdrawalAmount());
        return toDto(cashWithdrawalRepository.save(entity));
    }

    public void delete(String id) {
        cashWithdrawalRepository.deleteById(id);
    }

    private CashWithdrawalResponseDto toDto(CashWithdrawal entity) {
        CashWithdrawalResponseDto dto = new CashWithdrawalResponseDto();
        dto.setId(entity.getId());
        dto.setWithdrawalAmount(entity.getWithdrawalAmount());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
