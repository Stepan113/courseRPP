package org.course.courserpp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationForCashRequestDto {
    @NotNull(message = "Delay must not be null")
    @Positive(message = "Delay must be positive")
    private Long delay;

    @NotNull(message = "Withdrawal amount must not be null")
    @Positive(message = "Withdrawal amount must be positive")
    private Long withdrawalAmount;

    @NotBlank(message = "Card ID must not be blank")
    private String cardId;
}
