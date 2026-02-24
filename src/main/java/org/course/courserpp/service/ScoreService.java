package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ScoreDto;
import org.course.courserpp.model.Score;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.ScoreRepository;
import org.course.courserpp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    @Transactional
    public Score createScore(ScoreDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.getUserId()));
        Score score = new Score();
        score.setNumber(dto.getNumber());
        score.setBalance(dto.getBalance());
        score.setCurrency(dto.getCurrency());
        score.setUser(user);
        return scoreRepository.save(score);
    }

    public List<Score> getScoresByUser(String userId) {
        return scoreRepository.findAllByUserId(userId);
    }

    public Score getById(String id) {
        return scoreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Score not found: " + id));
    }
}
