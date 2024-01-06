package com.UserModel.UserModel.YearlyGoal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class YearlyGoalService {
    @Autowired
    private YearlyGoalRepository yearlyGoalRepository;

    public List<YearlyGoal> fetchAllYearlyGoals(){
        return yearlyGoalRepository.findAll();
    }

    public YearlyGoal saveYearlyGoal(YearlyGoal yearlyGoal){
        return yearlyGoalRepository.save(yearlyGoal);
    }

    public YearlyGoal updateYearlyGoal(Long id, YearlyGoal yearlyGoal){
        Optional<YearlyGoal> yearlyGoalCurrent = yearlyGoalRepository.findById(id);

        if(yearlyGoalCurrent.isPresent()){
            YearlyGoal data = yearlyGoalCurrent.get();
            data.setYg_Description(yearlyGoal.getYg_Description());
            data.setYg_Year(yearlyGoal.getYg_Year());
            data.setYg_Objective(yearlyGoal.getYg_Objective());
            data.setYg_Result(yearlyGoal.getYg_Result());
            data.setYg_Target_Completion_Date(yearlyGoal.getYg_Target_Completion_Date());
            data.setPriority_ID(yearlyGoal.getPriority_ID());
            data.setYg_Status(yearlyGoal.getYg_Status());
            data.setYg_Progress_Update(yearlyGoal.getYg_Progress_Update());
            data.setYg_Feedback(yearlyGoal.getYg_Feedback());
            data.setRating_ID(yearlyGoal.getRating_ID());
            data.setYg_Approval_Status(yearlyGoal.getYg_Approval_Status());
            data.setYg_Completion_Date(yearlyGoal.getYg_Completion_Date());

            return yearlyGoalRepository.save(data);

        } else {
            return null;
        }
    }

    public void deleteYearlyGoal (Long id){
        yearlyGoalRepository.deleteById(id);
    }
}
