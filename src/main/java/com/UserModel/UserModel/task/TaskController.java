package com.UserModel.UserModel.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/tasks/{userID}")
    public ResponseEntity<?> createTask( @PathVariable Long userID, @RequestBody Task task){

        try{
            Task response = taskService.createTask(task, userID);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> fetchAllTasks(){
        try{
            List<Task> response = taskService.listAllTasks();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/tasks/{taskID}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long taskID){
        try{
            var response = taskService.deleteTask(taskID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
