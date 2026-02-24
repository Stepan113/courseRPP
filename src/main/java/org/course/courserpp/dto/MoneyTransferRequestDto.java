package org.course.courserpp.dto;

import lombok.Data;

@Data
public class MoneyTransferRequestDto {
    private Long sum;
    private String scoreId;
}
