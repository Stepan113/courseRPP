package org.course.courserpp.mapper;

import org.course.courserpp.dto.request.ApplicationForCashRequestDto;
import org.course.courserpp.dto.response.ApplicationForCashResponseDto;
import org.course.courserpp.model.ApplicationForCash;
import org.springframework.stereotype.Component;

@Component
public class ApplicationForCashMapper {

    public ApplicationForCash toEntity(ApplicationForCashRequestDto dto) {
        ApplicationForCash entity = new ApplicationForCash();
        entity.setDelay(dto.getDelay());
        entity.setWithdrawalAmount(dto.getWithdrawalAmount());
        return entity;
    }

    public ApplicationForCashResponseDto toResponse(ApplicationForCash entity) {
        ApplicationForCashResponseDto dto = new ApplicationForCashResponseDto();
        dto.setId(entity.getId());
        dto.setDelay(entity.getDelay());
        dto.setWithdrawalAmount(entity.getWithdrawalAmount());
        return dto;
    }
}
