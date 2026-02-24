package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.ReplenishmentOperationRequestDto;
import org.course.courserpp.dto.response.ReplenishmentOperationResponseDto;
import org.course.courserpp.mapper.ReplenishmentOperationMapper;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.ReplenishmentOperation;
import org.course.courserpp.repository.CardRepository;
import org.course.courserpp.repository.ReplenishmentOperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplenishmentOperationService {

    private final ReplenishmentOperationRepository replenishmentOperationRepository;
    private final CardRepository cardRepository;
    private final ReplenishmentOperationMapper replenishmentOperationMapper;

    @Transactional(readOnly = true)
    public List<ReplenishmentOperationResponseDto> findAll() {
        return replenishmentOperationRepository.findAll().stream()
                .map(replenishmentOperationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReplenishmentOperationResponseDto findById(String id) {
        ReplenishmentOperation entity = replenishmentOperationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ReplenishmentOperation not found with id: " + id));
        return replenishmentOperationMapper.toResponse(entity);
    }

    @Transactional
    public ReplenishmentOperationResponseDto create(ReplenishmentOperationRequestDto dto) {
        Card card = cardRepository.findById(dto.getCardId())
                .orElseThrow(() -> new NoSuchElementException("Card not found with id: " + dto.getCardId()));
        ReplenishmentOperation entity = replenishmentOperationMapper.toEntity(dto);
        entity = replenishmentOperationRepository.save(entity);
        card.getReplenishmentOperations().add(entity);
        cardRepository.save(card);
        return replenishmentOperationMapper.toResponse(entity);
    }

    @Transactional
    public ReplenishmentOperationResponseDto update(String id, ReplenishmentOperationRequestDto dto) {
        ReplenishmentOperation entity = replenishmentOperationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ReplenishmentOperation not found with id: " + id));
        entity.setSum(dto.getSum());
        return replenishmentOperationMapper.toResponse(replenishmentOperationRepository.save(entity));
    }

    @Transactional
    public void delete(String id) {
        if (!replenishmentOperationRepository.existsById(id)) {
            throw new NoSuchElementException("ReplenishmentOperation not found with id: " + id);
        }
        replenishmentOperationRepository.deleteById(id);
    }
}
