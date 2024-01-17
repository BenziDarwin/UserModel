package com.UserModel.UserModel.QuarterlyGoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuarterlyGoalRepository extends JpaRepository<QuarterlyGoal,Long> {
    @Query("SELECT q FROM QuarterlyGoal q JOIN FETCH q.yearlyGoal")
    List<QuarterlyGoal> listQuarterlyGoalsAndYears();
}
