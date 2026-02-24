package org.course.courserpp.repository;

import org.course.courserpp.model.MoneyTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer, String> {

    @Query("SELECT mt FROM MoneyTransfer mt JOIN Score s ON mt MEMBER OF s.moneyTransfers WHERE s.id = :scoreId")
    List<MoneyTransfer> findAllByScoreId(@Param("scoreId") String scoreId);

    @Query("SELECT mt FROM MoneyTransfer mt JOIN Score s ON mt MEMBER OF s.moneyTransfers WHERE s.id = :scoreId AND mt.createdAt BETWEEN :from AND :to")
    List<MoneyTransfer> findAllByScoreIdAndPeriod(@Param("scoreId") String scoreId,
                                                   @Param("from") Date from,
                                                   @Param("to") Date to);
}
