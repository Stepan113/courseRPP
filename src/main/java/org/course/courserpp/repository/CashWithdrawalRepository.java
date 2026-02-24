package org.course.courserpp.repository;

import org.course.courserpp.model.CashWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashWithdrawalRepository extends JpaRepository<CashWithdrawal, String> {
}
