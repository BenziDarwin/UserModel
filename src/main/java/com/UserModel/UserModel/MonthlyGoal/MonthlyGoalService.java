package com.UserModel.UserModel.MonthlyGoal;

import com.UserModel.UserModel.QuarterlyGoal.QuarterlyGoal;
import com.UserModel.UserModel.QuarterlyGoal.QuarterlyGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonthlyGoalService {
    @Autowired
    private MonthlyGoalRepository monthlyGoalRepository;

    @Autowired
    private QuarterlyGoalRepository quarterlyGoalRepository;

    public List<MonthlyGoal> listMonthlyGoals(){
        return monthlyGoalRepository.findAll();
    }

    public MonthlyGoal findMonthlyGoal(Long id){
        return monthlyGoalRepository.findById(id).orElse(null);
    }

    public MonthlyGoal createMonthlyGoal(MonthlyGoal request, Long qtGoalID){

        Optional<QuarterlyGoal> qtGoal = quarterlyGoalRepository.findById(qtGoalID);

        if(qtGoal.isPresent()){
            MonthlyGoal monthlyGoal = MonthlyGoal
                    .builder()
                    .mt_Description(request.getMt_Description())
                    .mt_Month(request.getMt_Month())
                    .mt_Objective(request.getMt_Objective())
                    .mt_Result(request.getMt_Result())
                    .mt_Target_Completion_Date(request.getMt_Target_Completion_Date())
                    .priority_ID(request.getPriority_ID())
                    .mt_Status(request.getMt_Status())
                    .mt_Progress_Update(request.getMt_Progress_Update())
                    .mt_Feedback(request.getMt_Feedback())
                    .rating_ID(request.getRating_ID())
                    .mt_Approval_Status(request.getMt_Approval_Status())
                    .mt_Completion_Date(request.getMt_Completion_Date())
                    .quarterlyGoal(qtGoal.get())
                    .build();

            return monthlyGoalRepository.save(monthlyGoal);
        } else {
            return null;
        }
    }

    public MonthlyGoal updateMonthlyGoal(MonthlyGoal request, Long qtGoalID){
        Optional<QuarterlyGoal> quarterlyGoal = quarterlyGoalRepository.findById(qtGoalID);
        Optional<MonthlyGoal> monthlyGoal = monthlyGoalRepository.findById(request.getId());

        if(quarterlyGoal.isPresent() && monthlyGoal.isPresent()){

            MonthlyGoal goal = monthlyGoal.get();

            goal.setMt_Description(request.getMt_Description());
            goal.setMt_Month(request.getMt_Month());
            goal.setMt_Objective(request.getMt_Objective());
            goal.setMt_Result(request.getMt_Result());
            goal.setMt_Target_Completion_Date(request.getMt_Target_Completion_Date());
            goal.setPriority_ID(request.getPriority_ID());
            goal.setMt_Status(request.getMt_Status());
            goal.setMt_Progress_Update(request.getMt_Progress_Update());
            goal.setMt_Feedback(request.getMt_Feedback());
            goal.setRating_ID(request.getRating_ID());
            goal.setMt_Approval_Status(request.getMt_Approval_Status());
            goal.setMt_Completion_Date(request.getMt_Completion_Date());

            return monthlyGoalRepository.save(goal);
        } else {
            return null;
        }
    }
}
