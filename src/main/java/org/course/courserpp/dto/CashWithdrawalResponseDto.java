package org.course.courserpp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CashWithdrawalResponseDto {
    private String id;
    private Long withdrawalAmount;
    private Date createdAt;
}
