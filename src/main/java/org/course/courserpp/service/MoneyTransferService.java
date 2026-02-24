package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.MoneyTransferRequestDto;
import org.course.courserpp.dto.MoneyTransferResponseDto;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.repository.MoneyTransferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoneyTransferService {

    private final MoneyTransferRepository repository;

    public List<MoneyTransferResponseDto> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<MoneyTransferResponseDto> findById(String id) {
        return repository.findById(id).map(this::toResponseDto);
    }

    public MoneyTransferResponseDto create(MoneyTransferRequestDto dto) {
        MoneyTransfer entity = new MoneyTransfer();
        entity.setSum(dto.getSum());
        return toResponseDto(repository.save(entity));
    }

    public Optional<MoneyTransferResponseDto> update(String id, MoneyTransferRequestDto dto) {
        return repository.findById(id).map(entity -> {
            entity.setSum(dto.getSum());
            return toResponseDto(repository.save(entity));
        });
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    private MoneyTransferResponseDto toResponseDto(MoneyTransfer entity) {
        MoneyTransferResponseDto dto = new MoneyTransferResponseDto();
        dto.setId(entity.getId());
        dto.setSum(entity.getSum());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
