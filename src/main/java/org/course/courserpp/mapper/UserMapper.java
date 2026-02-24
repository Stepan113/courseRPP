package org.course.courserpp.mapper;

import org.course.courserpp.dto.request.UserRequestDto;
import org.course.courserpp.dto.response.UserResponseDto;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.Score;
import org.course.courserpp.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserResponseDto toResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());

        Set<String> cardIds = user.getCards() != null
                ? user.getCards().stream().map(Card::getId).collect(Collectors.toSet())
                : Collections.emptySet();
        dto.setCardIds(cardIds);

        Set<String> scoreIds = user.getScores() != null
                ? user.getScores().stream().map(Score::getId).collect(Collectors.toSet())
                : Collections.emptySet();
        dto.setScoreIds(scoreIds);

        return dto;
    }
}
