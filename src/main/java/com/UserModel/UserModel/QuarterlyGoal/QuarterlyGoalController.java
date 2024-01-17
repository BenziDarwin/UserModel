package com.UserModel.UserModel.QuarterlyGoal;

import com.UserModel.UserModel.YearlyGoal.YearlyGoal;
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
public class QuarterlyGoalController {

    @Autowired
    private QuarterlyGoalService quarterlyGoalService;

    @GetMapping("/quarterly-goals")
    public ResponseEntity<?> fetchAllQuarterlyGoals(){
        try{
            List<QuarterlyGoal> response = quarterlyGoalService.listAllQuarterlyGoals();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/quarterly-goals/{id}")
    public ResponseEntity<?> fetchQuarterlyGoal(@PathVariable Long id){
        try{
            QuarterlyGoal response = quarterlyGoalService.findOneQuarterlyGoal(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/quarterly-goals/{ygID}")
    public ResponseEntity<?> createQuarterlyGoal(@PathVariable Long ygID, @RequestBody QuarterlyGoal quarterlyGoal){
        try{
            QuarterlyGoal response = quarterlyGoalService.createQuarterlyGoal(quarterlyGoal, ygID);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/quarterly-goals/{ygID}")
    public ResponseEntity<?> updateQuarterlyGoal(@PathVariable Long ygID, @RequestBody QuarterlyGoal quarterlyGoal){
        try{
            QuarterlyGoal response = quarterlyGoalService.updateQuarterlyGoal(quarterlyGoal, ygID);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/quarterly-goals/{id}")
    public ResponseEntity<?> deleteQuarterlyGoal(@PathVariable Long id){
        Map<String,String> response = new HashMap<>();
        try{
            quarterlyGoalService.deleteQuarterlyGoal(id);
            response.put("response", "Deleted Successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/quarterly-goals-years")
    public ResponseEntity<?> listQuarterlyGoalsAndYears(){
        try{
            List<YearlyGoal> response = quarterlyGoalService.listQuarterlyGoalsAndYears();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
}
