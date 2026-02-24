package org.course.courserpp.dto;

import lombok.Data;

@Data
public class ScoreResponseDto {
    private String id;
    private String number;
    private String balance;
    private int currency;
    private String userId;
}
