package com.UserModel.UserModel.MonthlyGoal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class MonthlyGoalController {

    @Autowired
    private MonthlyGoalService monthlyGoalService;

    @GetMapping("/monthly-goals")
    public ResponseEntity<?> listMonthlyGoals(){
        try{
            List<MonthlyGoal> response = monthlyGoalService.listMonthlyGoals();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/monthly-goals/{qtGoalID}")
    public ResponseEntity<?> createMonthlyGoal(@PathVariable Long qtGoalID, @RequestBody MonthlyGoal monthlyGoal){
        try{
            MonthlyGoal response = monthlyGoalService.createMonthlyGoal(monthlyGoal, qtGoalID);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/monthly-goals/{mtGoalID}")
    public ResponseEntity<?> getMonthlyGoal(@PathVariable Long mtGoalID){
        try{
            MonthlyGoal response = monthlyGoalService.findMonthlyGoal(mtGoalID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/monthly-goals/{qtGoalID}")
    public ResponseEntity<?> updateMonthlyGoal(@PathVariable Long qtGoalID, @RequestBody MonthlyGoal monthlyGoal){
        try{
            MonthlyGoal response = monthlyGoalService.updateMonthlyGoal(monthlyGoal, qtGoalID);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
