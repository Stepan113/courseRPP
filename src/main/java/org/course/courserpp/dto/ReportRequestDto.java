package org.course.courserpp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReportRequestDto {
    private String scoreId;
    private Date from;
    private Date to;
    private String email;
}
