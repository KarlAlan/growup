package com.rcstc.growup.repository;

import com.rcstc.growup.domain.Consume;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Consume entity.
 */
public interface ConsumeRepository extends JpaRepository<Consume,Long> {

    @Query("select consume from Consume consume where consume.applyer.login = ?#{principal.username}")
    List<Consume> findByApplyerIsCurrentUser();

    @Query("select consume from Consume consume where consume.auditor.login = ?#{principal.username}")
    List<Consume> findByAuditorIsCurrentUser();

}
