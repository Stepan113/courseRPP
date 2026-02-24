package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.MoneyTransferRequestDto;
import org.course.courserpp.dto.MoneyTransferResponseDto;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.MoneyTransferRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoneyTransferService {

    private final MoneyTransferRepository moneyTransferRepository;
    private final ScoreRepository scoreRepository;

    public List<MoneyTransferResponseDto> findAll() {
        return moneyTransferRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public MoneyTransferResponseDto findById(String id) {
        return moneyTransferRepository.findById(id).map(this::toDto).orElseThrow();
    }

    public MoneyTransferResponseDto save(MoneyTransferRequestDto dto) {
        MoneyTransfer entity = new MoneyTransfer();
        entity.setSum(dto.getSum());
        MoneyTransfer saved = moneyTransferRepository.save(entity);
        Score score = scoreRepository.findById(dto.getScoreId()).orElseThrow();
        score.getMoneyTransfers().add(saved);
        scoreRepository.save(score);
        return toDto(saved);
    }

    public MoneyTransferResponseDto update(String id, MoneyTransferRequestDto dto) {
        MoneyTransfer entity = moneyTransferRepository.findById(id).orElseThrow();
        entity.setSum(dto.getSum());
        return toDto(moneyTransferRepository.save(entity));
    }

    public void delete(String id) {
        moneyTransferRepository.deleteById(id);
    }

    private MoneyTransferResponseDto toDto(MoneyTransfer entity) {
        MoneyTransferResponseDto dto = new MoneyTransferResponseDto();
        dto.setId(entity.getId());
        dto.setSum(entity.getSum());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
