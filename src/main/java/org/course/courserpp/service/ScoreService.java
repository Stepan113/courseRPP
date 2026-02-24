package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ScoreRequestDto;
import org.course.courserpp.dto.ScoreResponseDto;
import org.course.courserpp.model.Score;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.ScoreRepository;
import org.course.courserpp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    public List<ScoreResponseDto> findAll() {
        return scoreRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public ScoreResponseDto findById(String id) {
        return scoreRepository.findById(id).map(this::toDto).orElseThrow();
    }

    public ScoreResponseDto save(ScoreRequestDto dto) {
        Score score = new Score();
        score.setNumber(dto.getNumber());
        score.setBalance(dto.getBalance());
        score.setCurrency(dto.getCurrency());
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        score.setUser(user);
        return toDto(scoreRepository.save(score));
    }

    public ScoreResponseDto update(String id, ScoreRequestDto dto) {
        Score score = scoreRepository.findById(id).orElseThrow();
        score.setNumber(dto.getNumber());
        score.setBalance(dto.getBalance());
        score.setCurrency(dto.getCurrency());
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        score.setUser(user);
        return toDto(scoreRepository.save(score));
    }

    public void delete(String id) {
        scoreRepository.deleteById(id);
    }

    private ScoreResponseDto toDto(Score score) {
        ScoreResponseDto dto = new ScoreResponseDto();
        dto.setId(score.getId());
        dto.setNumber(score.getNumber());
        dto.setBalance(score.getBalance());
        dto.setCurrency(score.getCurrency());
        dto.setUserId(score.getUser() != null ? score.getUser().getId() : null);
        return dto;
    }
}
