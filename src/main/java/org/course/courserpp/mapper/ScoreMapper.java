package org.course.courserpp.mapper;

import org.course.courserpp.dto.request.ScoreRequestDto;
import org.course.courserpp.dto.response.ScoreResponseDto;
import org.course.courserpp.model.Score;
import org.course.courserpp.model.User;
import org.springframework.stereotype.Component;

@Component
public class ScoreMapper {

    public Score toEntity(ScoreRequestDto dto, User user) {
        Score score = new Score();
        score.setNumber(dto.getNumber());
        score.setBalance(dto.getBalance());
        score.setCurrency(dto.getCurrency());
        score.setUser(user);
        return score;
    }

    public ScoreResponseDto toResponse(Score score) {
        ScoreResponseDto dto = new ScoreResponseDto();
        dto.setId(score.getId());
        dto.setNumber(score.getNumber());
        dto.setBalance(score.getBalance());
        dto.setCurrency(score.getCurrency());
        dto.setUserId(score.getUser() != null ? score.getUser().getId() : null);
        return dto;
    }
}
