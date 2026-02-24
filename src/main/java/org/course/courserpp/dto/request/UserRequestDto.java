package org.course.courserpp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "Login must not be blank")
    private String login;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
