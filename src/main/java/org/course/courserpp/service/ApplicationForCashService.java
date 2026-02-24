package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ApplicationForCashRequestDto;
import org.course.courserpp.dto.ApplicationForCashResponseDto;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.repository.ApplicationForCashRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationForCashService {

    private final ApplicationForCashRepository repository;

    public List<ApplicationForCashResponseDto> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<ApplicationForCashResponseDto> findById(String id) {
        return repository.findById(id).map(this::toResponseDto);
    }

    public ApplicationForCashResponseDto create(ApplicationForCashRequestDto dto) {
        ApplicationForCash entity = new ApplicationForCash();
        entity.setDelay(dto.getDelay());
        entity.setWithdrawalAmount(dto.getWithdrawalAmount());
        return toResponseDto(repository.save(entity));
    }

    public Optional<ApplicationForCashResponseDto> update(String id, ApplicationForCashRequestDto dto) {
        return repository.findById(id).map(entity -> {
            entity.setDelay(dto.getDelay());
            entity.setWithdrawalAmount(dto.getWithdrawalAmount());
            return toResponseDto(repository.save(entity));
        });
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    private ApplicationForCashResponseDto toResponseDto(ApplicationForCash entity) {
        ApplicationForCashResponseDto dto = new ApplicationForCashResponseDto();
        dto.setId(entity.getId());
        dto.setDelay(entity.getDelay());
        dto.setWithdrawalAmount(entity.getWithdrawalAmount());
        return dto;
    }
}
