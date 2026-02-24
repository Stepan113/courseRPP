package org.course.courserpp.dto;

import lombok.Data;

@Data
public class CardRequestDto {
    private String number;
    private long balance;
    private String cvc;
    private String userId;
}
