package com.UserModel.UserModel.QuarterlyGoal;

import com.UserModel.UserModel.YearlyGoal.YearlyGoal;
import com.UserModel.UserModel.YearlyGoal.YearlyGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuarterlyGoalService {

    @Autowired
    private QuarterlyGoalRepository quarterlyGoalRepository;

    @Autowired
    private YearlyGoalRepository yearlyGoalRepository;

    public List<QuarterlyGoal> listAllQuarterlyGoals(){
        return quarterlyGoalRepository.findAll();
    }

    public QuarterlyGoal findOneQuarterlyGoal(Long id){
        return quarterlyGoalRepository.findById(id).orElse(null);
    }

    public void deleteQuarterlyGoal(Long id){
        quarterlyGoalRepository.deleteById(id);
    }

    public QuarterlyGoal createQuarterlyGoal(QuarterlyGoal request, Long ygID){
        Optional<YearlyGoal> yearlyGoal = yearlyGoalRepository.findById(ygID);

        if(yearlyGoal.isPresent()){
            QuarterlyGoal goal = QuarterlyGoal
                    .builder()
                    .qt_Description(request.getQt_Description())
                    .qt_Objective(request.getQt_Objective())
                    .qt_Result(request.getQt_Result())
                    .qt_Target_Completion_Date(request.getQt_Target_Completion_Date())
                    .priority_ID(request.getPriority_ID())
                    .qt_Status(request.getQt_Status())
                    .qt_Progress_Update(request.getQt_Progress_Update())
                    .qt_Feedback(request.getQt_Feedback())
                    .rating_ID(request.getRating_ID())
                    .qt_Approval_Status(request.getQt_Approval_Status())
                    .qt_Completion_Date(request.getQt_Completion_Date())
                    .qt_Period(request.getQt_Period())
                    .yearlyGoal(yearlyGoal.get())
                    .build();

            return quarterlyGoalRepository.save(goal);

        } else {
            return null;
        }

    }

    public QuarterlyGoal updateQuarterlyGoal(QuarterlyGoal request, Long ygID){

        Optional<YearlyGoal> yearlyGoal = yearlyGoalRepository.findById(ygID);
        Optional<QuarterlyGoal> quarterlyGoal = quarterlyGoalRepository.findById(request.getId());

        if(yearlyGoal.isPresent() && quarterlyGoal.isPresent()){

            QuarterlyGoal quarterlyGl = quarterlyGoal.get();

            quarterlyGl.setQt_Description(request.getQt_Description());
            quarterlyGl.setQt_Objective(request.getQt_Objective());
            quarterlyGl.setQt_Result(request.getQt_Result());
            quarterlyGl.setQt_Target_Completion_Date(request.getQt_Target_Completion_Date());
            quarterlyGl.setPriority_ID(request.getPriority_ID());
            quarterlyGl.setQt_Status(request.getQt_Status());
            quarterlyGl.setQt_Progress_Update(request.getQt_Progress_Update());
            quarterlyGl.setQt_Feedback(quarterlyGl.getQt_Feedback());
            quarterlyGl.setRating_ID(request.getRating_ID());
            quarterlyGl.setQt_Approval_Status(request.getQt_Approval_Status());
            quarterlyGl.setQt_Completion_Date(request.getQt_Completion_Date());
            quarterlyGl.setYearlyGoal(yearlyGoal.get());

            return quarterlyGoalRepository.save(quarterlyGl);

        } else {
            return null;
        }

    }

    public List<YearlyGoal> listQuarterlyGoalsAndYears(){
        return yearlyGoalRepository.findAll();
    }
}
