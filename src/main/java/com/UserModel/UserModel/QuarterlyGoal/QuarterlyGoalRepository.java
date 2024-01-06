package com.UserModel.UserModel.QuarterlyGoal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuarterlyGoalRepository extends JpaRepository<QuarterlyGoal,Long> {
}
