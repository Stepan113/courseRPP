package org.course.courserpp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReplenishmentOperationResponseDto {
    private String id;
    private Long sum;
    private Date createdAt;
}
