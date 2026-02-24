package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public List<Card> findByUser(User user) {
        return cardRepository.findByUser(user);
    }

    public Optional<Card> findById(String id) {
        return cardRepository.findById(id);
    }

    public Card save(Card card) {
        return cardRepository.save(card);
    }

    public void delete(String id) {
        cardRepository.deleteById(id);
    }
}
