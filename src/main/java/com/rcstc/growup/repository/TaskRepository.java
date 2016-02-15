package com.rcstc.growup.repository;

import com.rcstc.growup.domain.Task;

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

}
