package com.rcstc.growup.repository;

import com.rcstc.growup.domain.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Task entity.
 */
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("select task from Task task where task.contributor.login = ?#{principal.username}")
    List<Task> findByContributorIsCurrentUser();

    @Query("select task from Task task where task.auditor.login = ?#{principal.username}")
    List<Task> findByAuditorIsCurrentUser();

    @Query("select task from Task task where task.contributor.login = ?#{principal.username} order by task.declareDate desc")
    Page<Task> findByContributorOrderByDeclareDateDesc(String currentUserLogin, Pageable pageable);
}
