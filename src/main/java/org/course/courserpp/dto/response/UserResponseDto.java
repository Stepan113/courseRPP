package org.course.courserpp.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponseDto {
    private String id;
    private String login;
    private Set<String> cardIds;
    private Set<String> scoreIds;
}
