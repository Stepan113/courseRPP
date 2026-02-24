package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CashWithdrawalRequestDto;
import org.course.courserpp.dto.CashWithdrawalResponseDto;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.repository.CashWithdrawalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashWithdrawalService {

    private final CashWithdrawalRepository repository;

    public List<CashWithdrawalResponseDto> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<CashWithdrawalResponseDto> findById(String id) {
        return repository.findById(id).map(this::toResponseDto);
    }

    public CashWithdrawalResponseDto create(CashWithdrawalRequestDto dto) {
        CashWithdrawal entity = new CashWithdrawal();
        entity.setWithdrawalAmount(dto.getWithdrawalAmount());
        return toResponseDto(repository.save(entity));
    }

    public Optional<CashWithdrawalResponseDto> update(String id, CashWithdrawalRequestDto dto) {
        return repository.findById(id).map(entity -> {
            entity.setWithdrawalAmount(dto.getWithdrawalAmount());
            return toResponseDto(repository.save(entity));
        });
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    private CashWithdrawalResponseDto toResponseDto(CashWithdrawal entity) {
        CashWithdrawalResponseDto dto = new CashWithdrawalResponseDto();
        dto.setId(entity.getId());
        dto.setWithdrawalAmount(entity.getWithdrawalAmount());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
