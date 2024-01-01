package com.UserModel.UserModel.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> listAllTasks(){
        return taskRepository.findAll();
    }

    public Object deleteTask(Long taskID) {
        Map<String,Object> response = new HashMap<>();
         taskRepository.deleteById(taskID);
         response.put("success", true);
         return response;
    }
}
