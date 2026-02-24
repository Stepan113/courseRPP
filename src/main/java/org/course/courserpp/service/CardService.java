package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.CardRequestDto;
import org.course.courserpp.dto.response.CardResponseDto;
import org.course.courserpp.mapper.CardMapper;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.CardRepository;
import org.course.courserpp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;

    @Transactional(readOnly = true)
    public List<CardResponseDto> findAll() {
        return cardRepository.findAll().stream()
                .map(cardMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CardResponseDto findById(String id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Card not found with id: " + id));
        return cardMapper.toResponse(card);
    }

    @Transactional
    public CardResponseDto create(CardRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + dto.getUserId()));
        Card card = cardMapper.toEntity(dto, user);
        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Transactional
    public CardResponseDto update(String id, CardRequestDto dto) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Card not found with id: " + id));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + dto.getUserId()));
        card.setNumber(dto.getNumber());
        card.setBalance(dto.getBalance());
        card.setCVC(dto.getCvc());
        card.setUser(user);
        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Transactional
    public void delete(String id) {
        if (!cardRepository.existsById(id)) {
            throw new NoSuchElementException("Card not found with id: " + id);
        }
        cardRepository.deleteById(id);
    }
}
