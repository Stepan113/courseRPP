package org.course.courserpp.dto;

import lombok.Data;

import java.util.List;

@Data
public class MoneyTransferDto {
    private Long sum;
    private List<String> scoreIds;
}
