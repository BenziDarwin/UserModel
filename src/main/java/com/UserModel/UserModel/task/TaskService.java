package com.UserModel.UserModel.task;

import com.UserModel.UserModel.Roles.Roles;
import com.UserModel.UserModel.User.Persona;
import com.UserModel.UserModel.User.User;
import com.UserModel.UserModel.User.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UsersRepository usersRepository;

    public Task createTask(Task request, Long userId){

        try{

        Optional<User> user = usersRepository.findById(userId);

        if(user.isPresent()){
            User assignee = user.get();
            Task task = Task.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .completionDate(request.getCompletionDate())
                    .overdue(request.getOverdue())
                    .assignedTo(assignee)
                    .build();

            return taskRepository.save(task);

        } else{
            Task task = Task.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .completionDate(request.getCompletionDate())
                    .overdue(request.getOverdue())
                    .assignedTo(null)
                    .build();
            return taskRepository.save(task);
        }
        } catch (Exception e){
             throw e;
        }

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
