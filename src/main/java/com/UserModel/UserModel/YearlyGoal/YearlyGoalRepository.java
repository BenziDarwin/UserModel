package com.UserModel.UserModel.YearlyGoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlyGoalRepository extends JpaRepository<YearlyGoal,Long> {
}
