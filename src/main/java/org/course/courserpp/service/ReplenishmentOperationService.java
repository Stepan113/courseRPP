package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ReplenishmentOperationRequestDto;
import org.course.courserpp.dto.ReplenishmentOperationResponseDto;
import org.course.courserpp.model.ReplenishmentOperation;
import org.course.courserpp.repository.ReplenishmentOperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplenishmentOperationService {

    private final ReplenishmentOperationRepository repository;

    public List<ReplenishmentOperationResponseDto> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<ReplenishmentOperationResponseDto> findById(String id) {
        return repository.findById(id).map(this::toResponseDto);
    }

    public ReplenishmentOperationResponseDto create(ReplenishmentOperationRequestDto dto) {
        ReplenishmentOperation entity = new ReplenishmentOperation();
        entity.setSum(dto.getSum());
        return toResponseDto(repository.save(entity));
    }

    public Optional<ReplenishmentOperationResponseDto> update(String id, ReplenishmentOperationRequestDto dto) {
        return repository.findById(id).map(entity -> {
            entity.setSum(dto.getSum());
            return toResponseDto(repository.save(entity));
        });
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    private ReplenishmentOperationResponseDto toResponseDto(ReplenishmentOperation entity) {
        ReplenishmentOperationResponseDto dto = new ReplenishmentOperationResponseDto();
        dto.setId(entity.getId());
        dto.setSum(entity.getSum());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
