package org.course.courserpp.dto;

import lombok.Data;

@Data
public class ApplicationForCashResponseDto {
    private String id;
    private Long delay;
    private Long withdrawalAmount;
}
