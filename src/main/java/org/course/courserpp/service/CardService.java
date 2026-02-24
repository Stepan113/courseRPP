package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CardRequestDto;
import org.course.courserpp.dto.CardResponseDto;
import org.course.courserpp.model.Card;
import org.course.courserpp.repository.CardRepository;
import org.course.courserpp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public List<CardResponseDto> findAll() {
        return cardRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<CardResponseDto> findById(String id) {
        return cardRepository.findById(id).map(this::toResponseDto);
    }

    public CardResponseDto create(CardRequestDto dto) {
        Card card = new Card();
        card.setNumber(dto.getNumber());
        card.setBalance(dto.getBalance());
        card.setCVC(dto.getCvc());
        if (dto.getUserId() != null) {
            userRepository.findById(dto.getUserId()).ifPresent(card::setUser);
        }
        return toResponseDto(cardRepository.save(card));
    }

    public Optional<CardResponseDto> update(String id, CardRequestDto dto) {
        return cardRepository.findById(id).map(card -> {
            card.setNumber(dto.getNumber());
            card.setBalance(dto.getBalance());
            card.setCVC(dto.getCvc());
            if (dto.getUserId() != null) {
                userRepository.findById(dto.getUserId()).ifPresent(card::setUser);
            }
            return toResponseDto(cardRepository.save(card));
        });
    }

    public void delete(String id) {
        cardRepository.deleteById(id);
    }

    private CardResponseDto toResponseDto(Card card) {
        CardResponseDto dto = new CardResponseDto();
        dto.setId(card.getId());
        dto.setNumber(card.getNumber());
        dto.setBalance(card.getBalance());
        dto.setCvc(card.getCVC());
        if (card.getUser() != null) {
            dto.setUserId(card.getUser().getId());
            dto.setUserLogin(card.getUser().getLogin());
        }
        return dto;
    }
}
