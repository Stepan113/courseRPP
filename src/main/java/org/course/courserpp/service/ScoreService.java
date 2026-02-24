package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.Score;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public List<Score> findByUser(User user) {
        return scoreRepository.findByUser(user);
    }

    public Optional<Score> findById(String id) {
        return scoreRepository.findById(id);
    }

    public Score save(Score score) {
        return scoreRepository.save(score);
    }

    public void delete(String id) {
        scoreRepository.deleteById(id);
    }

    public List<Score> findAll() {
        return scoreRepository.findAll();
    }
}
