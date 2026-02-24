package org.course.courserpp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplenishmentOperationRequestDto {
    @NotNull(message = "Sum must not be null")
    @Positive(message = "Sum must be positive")
    private Long sum;

    @NotBlank(message = "Card ID must not be blank")
    private String cardId;
}
