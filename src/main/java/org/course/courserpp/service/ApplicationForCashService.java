package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ApplicationForCashRequestDto;
import org.course.courserpp.dto.ApplicationForCashResponseDto;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.model.Card;
import org.course.courserpp.repository.ApplicationForCashRepository;
import org.course.courserpp.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationForCashService {

    private final ApplicationForCashRepository applicationForCashRepository;
    private final CardRepository cardRepository;

    public List<ApplicationForCashResponseDto> findAll() {
        return applicationForCashRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public ApplicationForCashResponseDto findById(String id) {
        return applicationForCashRepository.findById(id).map(this::toDto).orElseThrow();
    }

    public ApplicationForCashResponseDto save(ApplicationForCashRequestDto dto) {
        ApplicationForCash entity = new ApplicationForCash();
        entity.setDelay(dto.getDelay());
        entity.setWithdrawalAmount(dto.getWithdrawalAmount());
        ApplicationForCash saved = applicationForCashRepository.save(entity);
        Card card = cardRepository.findById(dto.getCardId()).orElseThrow();
        card.getApplicationForCashes().add(saved);
        cardRepository.save(card);
        return toDto(saved);
    }

    public ApplicationForCashResponseDto update(String id, ApplicationForCashRequestDto dto) {
        ApplicationForCash entity = applicationForCashRepository.findById(id).orElseThrow();
        entity.setDelay(dto.getDelay());
        entity.setWithdrawalAmount(dto.getWithdrawalAmount());
        return toDto(applicationForCashRepository.save(entity));
    }

    public void delete(String id) {
        applicationForCashRepository.deleteById(id);
    }

    private ApplicationForCashResponseDto toDto(ApplicationForCash entity) {
        ApplicationForCashResponseDto dto = new ApplicationForCashResponseDto();
        dto.setId(entity.getId());
        dto.setDelay(entity.getDelay());
        dto.setWithdrawalAmount(entity.getWithdrawalAmount());
        return dto;
    }
}
