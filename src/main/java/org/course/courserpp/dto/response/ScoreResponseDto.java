package org.course.courserpp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreResponseDto {
    private String id;
    private String number;
    private String balance;
    private int currency;
    private String userId;
}
