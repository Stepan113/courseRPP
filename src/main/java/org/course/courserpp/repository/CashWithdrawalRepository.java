package org.course.courserpp.repository;

import org.course.courserpp.model.CashWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CashWithdrawalRepository extends JpaRepository<CashWithdrawal, String> {

    @Query("SELECT cw FROM CashWithdrawal cw JOIN Score s ON cw MEMBER OF s.cashWithdrawals WHERE s.id = :scoreId")
    List<CashWithdrawal> findAllByScoreId(@Param("scoreId") String scoreId);

    @Query("SELECT cw FROM CashWithdrawal cw JOIN Score s ON cw MEMBER OF s.cashWithdrawals WHERE s.id = :scoreId AND cw.createdAt BETWEEN :from AND :to")
    List<CashWithdrawal> findAllByScoreIdAndPeriod(@Param("scoreId") String scoreId,
                                                    @Param("from") Date from,
                                                    @Param("to") Date to);
}
