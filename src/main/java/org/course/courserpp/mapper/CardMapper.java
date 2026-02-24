package org.course.courserpp.mapper;

import org.course.courserpp.dto.request.CardRequestDto;
import org.course.courserpp.dto.response.CardResponseDto;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.User;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public Card toEntity(CardRequestDto dto, User user) {
        Card card = new Card();
        card.setNumber(dto.getNumber());
        card.setBalance(dto.getBalance());
        card.setCVC(dto.getCvc());
        card.setUser(user);
        return card;
    }

    public CardResponseDto toResponse(Card card) {
        CardResponseDto dto = new CardResponseDto();
        dto.setId(card.getId());
        dto.setNumber(card.getNumber());
        dto.setBalance(card.getBalance());
        dto.setCvc(card.getCVC());
        dto.setUserId(card.getUser() != null ? card.getUser().getId() : null);
        return dto;
    }
}
