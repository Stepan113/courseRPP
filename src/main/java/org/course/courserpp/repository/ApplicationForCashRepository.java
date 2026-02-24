package org.course.courserpp.repository;

import org.course.courserpp.model.ApplicationForCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationForCashRepository extends JpaRepository<ApplicationForCash, String> {

    @Query("SELECT a FROM ApplicationForCash a JOIN Card c ON a MEMBER OF c.applicationForCashes JOIN Score s ON s.user = c.user WHERE s.id = :scoreId")
    List<ApplicationForCash> findAllByScoreId(@Param("scoreId") String scoreId);
}
