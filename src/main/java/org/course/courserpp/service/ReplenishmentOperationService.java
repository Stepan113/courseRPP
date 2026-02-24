package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ReplenishmentOperationRequestDto;
import org.course.courserpp.dto.ReplenishmentOperationResponseDto;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.ReplenishmentOperation;
import org.course.courserpp.repository.CardRepository;
import org.course.courserpp.repository.ReplenishmentOperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplenishmentOperationService {

    private final ReplenishmentOperationRepository replenishmentOperationRepository;
    private final CardRepository cardRepository;

    public List<ReplenishmentOperationResponseDto> findAll() {
        return replenishmentOperationRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public ReplenishmentOperationResponseDto findById(String id) {
        return replenishmentOperationRepository.findById(id).map(this::toDto).orElseThrow();
    }

    public ReplenishmentOperationResponseDto save(ReplenishmentOperationRequestDto dto) {
        ReplenishmentOperation entity = new ReplenishmentOperation();
        entity.setSum(dto.getSum());
        ReplenishmentOperation saved = replenishmentOperationRepository.save(entity);
        Card card = cardRepository.findById(dto.getCardId()).orElseThrow();
        card.getReplenishmentOperations().add(saved);
        cardRepository.save(card);
        return toDto(saved);
    }

    public ReplenishmentOperationResponseDto update(String id, ReplenishmentOperationRequestDto dto) {
        ReplenishmentOperation entity = replenishmentOperationRepository.findById(id).orElseThrow();
        entity.setSum(dto.getSum());
        return toDto(replenishmentOperationRepository.save(entity));
    }

    public void delete(String id) {
        replenishmentOperationRepository.deleteById(id);
    }

    private ReplenishmentOperationResponseDto toDto(ReplenishmentOperation entity) {
        ReplenishmentOperationResponseDto dto = new ReplenishmentOperationResponseDto();
        dto.setId(entity.getId());
        dto.setSum(entity.getSum());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
