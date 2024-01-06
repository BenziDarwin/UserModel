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

    public YearlyGoalController(YearlyGoalService yearlyGoalService) {
        this.yearlyGoalService = yearlyGoalService;
    }

    @GetMapping("/yearly-goals")
    public ResponseEntity<?> fetchAllYearlyGoals (){
        try{
            List<YearlyGoal> response = yearlyGoalService.fetchAllYearlyGoals();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/yearly-goals/{yearlyGoalID}")
    public ResponseEntity<?> fetchSingleYearlyGoal(@PathVariable Long yearlyGoalID){
        try{
            YearlyGoal response = yearlyGoalService.findYearlyGoal(yearlyGoalID);
            if(response != null){
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
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
