package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.ReplenishmentOperation;
import org.course.courserpp.repository.ReplenishmentOperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplenishmentOperationService {

    private final ReplenishmentOperationRepository replenishmentOperationRepository;

    public List<ReplenishmentOperation> findAll() {
        return replenishmentOperationRepository.findAll();
    }

    public Optional<ReplenishmentOperation> findById(String id) {
        return replenishmentOperationRepository.findById(id);
    }

    public ReplenishmentOperation save(ReplenishmentOperation operation) {
        return replenishmentOperationRepository.save(operation);
    }

    public void delete(String id) {
        replenishmentOperationRepository.deleteById(id);
    }
}
