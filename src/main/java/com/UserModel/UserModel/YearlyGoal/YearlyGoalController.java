package com.UserModel.UserModel.YearlyGoal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class YearlyGoalController {

    @Autowired
    private YearlyGoalService yearlyGoalService;

    @GetMapping("/yearly-goals")
    public ResponseEntity<?> fetchAllYearlyGoals (){
        try{
            List<YearlyGoal> response = yearlyGoalService.fetchAllYearlyGoals();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/yearly-goals")
    public ResponseEntity<?> createYearlyGoal(@RequestBody YearlyGoal yearlyGoal){
        try{
            YearlyGoal response = yearlyGoalService.saveYearlyGoal(yearlyGoal);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/yearly-goals/{yearlyGoalID}")
    public ResponseEntity<?> updateYearlyGoal(@PathVariable Long yearlyGoalID, @RequestBody YearlyGoal yearlyGoal){
        try{
            YearlyGoal response = yearlyGoalService.updateYearlyGoal(yearlyGoalID,yearlyGoal);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/yearly-goals/{yearlyGoalID}")
    public ResponseEntity<?> deleteYearlyGoal(@PathVariable Long yearlyGoalID){
        Map<String,String> response = new HashMap<>();
        try{
            yearlyGoalService.deleteYearlyGoal(yearlyGoalID);
            response.put("response", "Deleted Successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
