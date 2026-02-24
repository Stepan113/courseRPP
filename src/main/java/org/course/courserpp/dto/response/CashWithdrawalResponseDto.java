package org.course.courserpp.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CashWithdrawalResponseDto {
    private String id;
    private Long withdrawalAmount;
    private Date createdAt;
}
