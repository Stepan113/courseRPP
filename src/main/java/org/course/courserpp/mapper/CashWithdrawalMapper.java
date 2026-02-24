package org.course.courserpp.mapper;

import org.course.courserpp.dto.request.CashWithdrawalRequestDto;
import org.course.courserpp.dto.response.CashWithdrawalResponseDto;
import org.course.courserpp.model.CashWithdrawal;
import org.springframework.stereotype.Component;

@Component
public class CashWithdrawalMapper {

    public CashWithdrawal toEntity(CashWithdrawalRequestDto dto) {
        CashWithdrawal entity = new CashWithdrawal();
        entity.setWithdrawalAmount(dto.getWithdrawalAmount());
        return entity;
    }

    public CashWithdrawalResponseDto toResponse(CashWithdrawal entity) {
        CashWithdrawalResponseDto dto = new CashWithdrawalResponseDto();
        dto.setId(entity.getId());
        dto.setWithdrawalAmount(entity.getWithdrawalAmount());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
