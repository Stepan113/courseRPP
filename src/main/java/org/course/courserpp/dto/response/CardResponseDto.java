package org.course.courserpp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponseDto {
    private String id;
    private String number;
    private long balance;
    private String cvc;
    private String userId;
}
