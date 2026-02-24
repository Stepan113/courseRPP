package org.course.courserpp.service;

import org.course.courserpp.model.Score;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public List<Score> findByUserId(String userId) {
        return scoreRepository.findByUserId(userId);
    }

    public Optional<Score> findById(String id) {
        return scoreRepository.findById(id);
    }

    public Score save(Score score) {
        return scoreRepository.save(score);
    }
}
