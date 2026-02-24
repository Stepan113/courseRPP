package org.course.courserpp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequestDto {
    @NotBlank(message = "Card number must not be blank")
    private String number;

    @PositiveOrZero(message = "Balance must be zero or positive")
    private long balance;

    @NotBlank(message = "CVC must not be blank")
    private String cvc;

    @NotBlank(message = "User ID must not be blank")
    private String userId;
}
