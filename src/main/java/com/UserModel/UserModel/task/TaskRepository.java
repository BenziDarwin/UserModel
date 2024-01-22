package com.UserModel.UserModel.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.assignedTo.id = :userId")
    List<Task> findTasksByUserId(Long userId);
    @Modifying
    @Query("UPDATE MonthlyGoal m SET m.mt_Status = CASE WHEN (SELECT COUNT(t.id) FROM Task t WHERE t.monthlyGoal.id = :monthlyGoalId AND t.completed = false) = 0 THEN 'complete' ELSE 'incomplete' END WHERE m.id = :monthlyGoalId")
    @Transactional
    void completeGoal(Long monthlyGoalId);

}

