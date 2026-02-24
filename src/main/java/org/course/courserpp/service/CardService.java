package org.course.courserpp.service;

import org.course.courserpp.model.Card;
import org.course.courserpp.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public List<Card> findByUserId(String userId) {
        return cardRepository.findByUserId(userId);
    }

    public Optional<Card> findById(String id) {
        return cardRepository.findById(id);
    }

    public Card save(Card card) {
        return cardRepository.save(card);
    }
}
