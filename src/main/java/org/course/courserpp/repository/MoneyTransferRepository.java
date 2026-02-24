package org.course.courserpp.repository;

import org.course.courserpp.model.MoneyTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer, String> {
}
