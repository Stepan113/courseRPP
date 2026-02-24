package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.dto.request.ScoreRequestDto;
import org.course.courserpp.dto.response.ScoreResponseDto;
import org.course.courserpp.mapper.ScoreMapper;
import org.course.courserpp.model.Score;
import org.course.courserpp.model.User;
import org.course.courserpp.repository.ScoreRepository;
import org.course.courserpp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final ScoreMapper scoreMapper;

    @Transactional(readOnly = true)
    public List<ScoreResponseDto> findAll() {
        return scoreRepository.findAll().stream()
                .map(scoreMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ScoreResponseDto findById(String id) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Score not found with id: " + id));
        return scoreMapper.toResponse(score);
    }

    @Transactional
    public ScoreResponseDto create(ScoreRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + dto.getUserId()));
        Score score = scoreMapper.toEntity(dto, user);
        return scoreMapper.toResponse(scoreRepository.save(score));
    }

    @Transactional
    public ScoreResponseDto update(String id, ScoreRequestDto dto) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Score not found with id: " + id));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + dto.getUserId()));
        score.setNumber(dto.getNumber());
        score.setBalance(dto.getBalance());
        score.setCurrency(dto.getCurrency());
        score.setUser(user);
        return scoreMapper.toResponse(scoreRepository.save(score));
    }

    @Transactional
    public void delete(String id) {
        if (!scoreRepository.existsById(id)) {
            throw new NoSuchElementException("Score not found with id: " + id);
        }
        scoreRepository.deleteById(id);
    }
}
