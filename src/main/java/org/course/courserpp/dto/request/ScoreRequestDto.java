package org.course.courserpp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreRequestDto {
    @NotBlank(message = "Score number must not be blank")
    private String number;

    @NotBlank(message = "Balance must not be blank")
    private String balance;

    private int currency;

    @NotBlank(message = "User ID must not be blank")
    private String userId;
}
