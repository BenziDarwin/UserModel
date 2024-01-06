package com.UserModel.UserModel.MonthlyGoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyGoalRepository extends JpaRepository<MonthlyGoal,Long> {
}
