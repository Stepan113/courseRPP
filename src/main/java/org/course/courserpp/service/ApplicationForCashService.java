package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.ApplicationForCashRequestDto;
import org.course.courserpp.dto.response.ApplicationForCashResponseDto;
import org.course.courserpp.mapper.ApplicationForCashMapper;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.model.Card;
import org.course.courserpp.repository.ApplicationForCashRepository;
import org.course.courserpp.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationForCashService {

    private final ApplicationForCashRepository applicationForCashRepository;
    private final CardRepository cardRepository;
    private final ApplicationForCashMapper applicationForCashMapper;

    @Transactional(readOnly = true)
    public List<ApplicationForCashResponseDto> findAll() {
        return applicationForCashRepository.findAll().stream()
                .map(applicationForCashMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApplicationForCashResponseDto findById(String id) {
        ApplicationForCash entity = applicationForCashRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ApplicationForCash not found with id: " + id));
        return applicationForCashMapper.toResponse(entity);
    }

    @Transactional
    public ApplicationForCashResponseDto create(ApplicationForCashRequestDto dto) {
        Card card = cardRepository.findById(dto.getCardId())
                .orElseThrow(() -> new NoSuchElementException("Card not found with id: " + dto.getCardId()));
        ApplicationForCash entity = applicationForCashMapper.toEntity(dto);
        entity = applicationForCashRepository.save(entity);
        card.getApplicationForCashes().add(entity);
        cardRepository.save(card);
        return applicationForCashMapper.toResponse(entity);
    }

    @Transactional
    public ApplicationForCashResponseDto update(String id, ApplicationForCashRequestDto dto) {
        ApplicationForCash entity = applicationForCashRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ApplicationForCash not found with id: " + id));
        entity.setDelay(dto.getDelay());
        entity.setWithdrawalAmount(dto.getWithdrawalAmount());
        return applicationForCashMapper.toResponse(applicationForCashRepository.save(entity));
    }

    @Transactional
    public void delete(String id) {
        if (!applicationForCashRepository.existsById(id)) {
            throw new NoSuchElementException("ApplicationForCash not found with id: " + id);
        }
        applicationForCashRepository.deleteById(id);
    }
}
