package org.course.courserpp.mapper;

import org.course.courserpp.dto.request.MoneyTransferRequestDto;
import org.course.courserpp.dto.response.MoneyTransferResponseDto;
import org.course.courserpp.model.MoneyTransfer;
import org.springframework.stereotype.Component;

@Component
public class MoneyTransferMapper {

    public MoneyTransfer toEntity(MoneyTransferRequestDto dto) {
        MoneyTransfer entity = new MoneyTransfer();
        entity.setSum(dto.getSum());
        return entity;
    }

    public MoneyTransferResponseDto toResponse(MoneyTransfer entity) {
        MoneyTransferResponseDto dto = new MoneyTransferResponseDto();
        dto.setId(entity.getId());
        dto.setSum(entity.getSum());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
