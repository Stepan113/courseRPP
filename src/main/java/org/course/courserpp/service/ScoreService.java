package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.ScoreRequestDto;
import org.course.courserpp.dto.ScoreResponseDto;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.ScoreRepository;
import org.course.courserpp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    public List<ScoreResponseDto> findAll() {
        return scoreRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<ScoreResponseDto> findById(String id) {
        return scoreRepository.findById(id).map(this::toResponseDto);
    }

    public ScoreResponseDto create(ScoreRequestDto dto) {
        Score score = new Score();
        score.setNumber(dto.getNumber());
        score.setBalance(dto.getBalance());
        score.setCurrency(dto.getCurrency());
        if (dto.getUserId() != null) {
            userRepository.findById(dto.getUserId()).ifPresent(score::setUser);
        }
        return toResponseDto(scoreRepository.save(score));
    }

    public Optional<ScoreResponseDto> update(String id, ScoreRequestDto dto) {
        return scoreRepository.findById(id).map(score -> {
            score.setNumber(dto.getNumber());
            score.setBalance(dto.getBalance());
            score.setCurrency(dto.getCurrency());
            if (dto.getUserId() != null) {
                userRepository.findById(dto.getUserId()).ifPresent(score::setUser);
            }
            return toResponseDto(scoreRepository.save(score));
        });
    }

    public void delete(String id) {
        scoreRepository.deleteById(id);
    }

    private ScoreResponseDto toResponseDto(Score score) {
        ScoreResponseDto dto = new ScoreResponseDto();
        dto.setId(score.getId());
        dto.setNumber(score.getNumber());
        dto.setBalance(score.getBalance());
        dto.setCurrency(score.getCurrency());
        if (score.getUser() != null) {
            dto.setUserId(score.getUser().getId());
            dto.setUserLogin(score.getUser().getLogin());
        }
        return dto;
    }
}
