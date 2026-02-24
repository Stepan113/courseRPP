package org.course.courserpp.repository;

import org.course.courserpp.model.CashWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashWithdrawalRepository extends JpaRepository<CashWithdrawal, String> {
}
