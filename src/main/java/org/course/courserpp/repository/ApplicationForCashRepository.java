package org.course.courserpp.repository;

import org.course.courserpp.model.ApplicationForCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationForCashRepository extends JpaRepository<ApplicationForCash, String> {
}
