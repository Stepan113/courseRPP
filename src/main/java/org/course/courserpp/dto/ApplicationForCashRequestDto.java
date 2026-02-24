package org.course.courserpp.dto;

import lombok.Data;

@Data
public class ApplicationForCashRequestDto {
    private Long delay;
    private Long withdrawalAmount;
}
