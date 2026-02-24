package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.CardRequestDto;
import org.course.courserpp.dto.CardResponseDto;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.CardRepository;
import org.course.courserpp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public List<CardResponseDto> findAll() {
        return cardRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public CardResponseDto findById(String id) {
        return cardRepository.findById(id).map(this::toDto).orElseThrow();
    }

    public CardResponseDto save(CardRequestDto dto) {
        Card card = new Card();
        card.setNumber(dto.getNumber());
        card.setBalance(dto.getBalance());
        card.setCVC(dto.getCvc());
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        card.setUser(user);
        return toDto(cardRepository.save(card));
    }

    public CardResponseDto update(String id, CardRequestDto dto) {
        Card card = cardRepository.findById(id).orElseThrow();
        card.setNumber(dto.getNumber());
        card.setBalance(dto.getBalance());
        card.setCVC(dto.getCvc());
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        card.setUser(user);
        return toDto(cardRepository.save(card));
    }

    public void delete(String id) {
        cardRepository.deleteById(id);
    }

    private CardResponseDto toDto(Card card) {
        CardResponseDto dto = new CardResponseDto();
        dto.setId(card.getId());
        dto.setNumber(card.getNumber());
        dto.setBalance(card.getBalance());
        dto.setCvc(card.getCVC());
        dto.setUserId(card.getUser() != null ? card.getUser().getId() : null);
        return dto;
    }
}
