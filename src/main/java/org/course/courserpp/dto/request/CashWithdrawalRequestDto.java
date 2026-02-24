package org.course.courserpp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashWithdrawalRequestDto {
    @NotNull(message = "Withdrawal amount must not be null")
    @Positive(message = "Withdrawal amount must be positive")
    private Long withdrawalAmount;

    @NotBlank(message = "Score ID must not be blank")
    private String scoreId;
}
