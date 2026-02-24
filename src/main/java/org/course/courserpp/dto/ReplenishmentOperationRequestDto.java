package org.course.courserpp.dto;

import lombok.Data;

@Data
public class ReplenishmentOperationRequestDto {
    private Long sum;
    private String cardId;
}
