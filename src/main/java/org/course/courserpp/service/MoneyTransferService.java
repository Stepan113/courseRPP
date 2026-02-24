package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.MoneyTransferRequestDto;
import org.course.courserpp.dto.response.MoneyTransferResponseDto;
import org.course.courserpp.mapper.MoneyTransferMapper;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.MoneyTransferRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoneyTransferService {

    private final MoneyTransferRepository moneyTransferRepository;
    private final ScoreRepository scoreRepository;
    private final MoneyTransferMapper moneyTransferMapper;

    @Transactional(readOnly = true)
    public List<MoneyTransferResponseDto> findAll() {
        return moneyTransferRepository.findAll().stream()
                .map(moneyTransferMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MoneyTransferResponseDto findById(String id) {
        MoneyTransfer entity = moneyTransferRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("MoneyTransfer not found with id: " + id));
        return moneyTransferMapper.toResponse(entity);
    }

    @Transactional
    public MoneyTransferResponseDto create(MoneyTransferRequestDto dto) {
        Score fromScore = scoreRepository.findById(dto.getFromScoreId())
                .orElseThrow(() -> new NoSuchElementException("Source score not found with id: " + dto.getFromScoreId()));
        Score toScore = scoreRepository.findById(dto.getToScoreId())
                .orElseThrow(() -> new NoSuchElementException("Target score not found with id: " + dto.getToScoreId()));
        MoneyTransfer entity = moneyTransferMapper.toEntity(dto);
        entity = moneyTransferRepository.save(entity);
        fromScore.getMoneyTransfers().add(entity);
        toScore.getMoneyTransfers().add(entity);
        scoreRepository.save(fromScore);
        scoreRepository.save(toScore);
        return moneyTransferMapper.toResponse(entity);
    }

    @Transactional
    public MoneyTransferResponseDto update(String id, MoneyTransferRequestDto dto) {
        MoneyTransfer entity = moneyTransferRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("MoneyTransfer not found with id: " + id));
        entity.setSum(dto.getSum());
        return moneyTransferMapper.toResponse(moneyTransferRepository.save(entity));
    }

    @Transactional
    public void delete(String id) {
        if (!moneyTransferRepository.existsById(id)) {
            throw new NoSuchElementException("MoneyTransfer not found with id: " + id);
        }
        moneyTransferRepository.deleteById(id);
    }
}
