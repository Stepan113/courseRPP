package org.course.courserpp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationForCashResponseDto {
    private String id;
    private Long delay;
    private Long withdrawalAmount;
}
