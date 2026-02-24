package org.course.courserpp.repository;

import org.course.courserpp.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, String> {
    List<Score> findByUserId(String userId);
}
