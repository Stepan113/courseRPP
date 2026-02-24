package org.course.courserpp.repository;

import org.course.courserpp.model.ReplenishmentOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplenishmentOperationRepository extends JpaRepository<ReplenishmentOperation, String> {
}
