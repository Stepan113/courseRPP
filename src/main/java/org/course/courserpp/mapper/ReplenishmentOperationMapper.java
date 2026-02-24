package org.course.courserpp.mapper;

import org.course.courserpp.dto.request.ReplenishmentOperationRequestDto;
import org.course.courserpp.dto.response.ReplenishmentOperationResponseDto;
import org.course.courserpp.model.ReplenishmentOperation;
import org.springframework.stereotype.Component;

@Component
public class ReplenishmentOperationMapper {

    public ReplenishmentOperation toEntity(ReplenishmentOperationRequestDto dto) {
        ReplenishmentOperation entity = new ReplenishmentOperation();
        entity.setSum(dto.getSum());
        return entity;
    }

    public ReplenishmentOperationResponseDto toResponse(ReplenishmentOperation entity) {
        ReplenishmentOperationResponseDto dto = new ReplenishmentOperationResponseDto();
        dto.setId(entity.getId());
        dto.setSum(entity.getSum());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
