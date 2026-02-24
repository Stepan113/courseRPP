package org.course.courserpp.dto;

import lombok.Data;

@Data
public class CardResponseDto {
    private String id;
    private String number;
    private long balance;
    private String cvc;
    private String userId;
    private String userLogin;
}
