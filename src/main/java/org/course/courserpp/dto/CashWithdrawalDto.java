package org.course.courserpp.dto;

import lombok.Data;

import java.util.List;

@Data
public class CashWithdrawalDto {
    private Long withdrawalAmount;
    private List<String> scoreIds;
    private String applicationForCashId;
}
