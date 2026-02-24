package org.course.courserpp.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReplenishmentOperationResponseDto {
    private String id;
    private Long sum;
    private Date createdAt;
}
