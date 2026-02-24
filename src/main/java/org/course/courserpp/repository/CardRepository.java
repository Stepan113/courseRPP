package org.course.courserpp.repository;

import org.course.courserpp.model.Card;
import org.course.courserpp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, String> {
    List<Card> findByUser(User user);
}
