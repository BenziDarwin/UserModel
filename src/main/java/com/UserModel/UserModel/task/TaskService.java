package com.UserModel.UserModel.task;

import com.UserModel.UserModel.MonthlyGoal.MonthlyGoal;
import com.UserModel.UserModel.MonthlyGoal.MonthlyGoalRepository;
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

    @Autowired
    private MonthlyGoalRepository monthlyGoalRepository;

    public Task createTask(Task request, Long userId, Long mtID){

        Optional<User> user = usersRepository.findById(userId);
        Optional<MonthlyGoal> monthlyGoal = monthlyGoalRepository.findById(mtID);

        if(user.isPresent() && monthlyGoal.isPresent()){
            User assignee = user.get();
            Task task = Task.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .completionDate(request.getCompletionDate())
                    .overdue(request.getOverdue())
                    .assignedTo(assignee)
                    .monthlyGoal(monthlyGoal.get())
                    .build();

            return taskRepository.save(task);

        } else{
            return null;
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

    public Task updateTask(Task request, Long assignedTo, Long mtID) {
        try{
            Optional<User> user = usersRepository.findById(assignedTo);
            Optional<Task> task = taskRepository.findById(request.getId());
            Optional<MonthlyGoal> monthlyGoal = monthlyGoalRepository.findById(mtID);

            if(user.isPresent() && task.isPresent() && monthlyGoal.isPresent()){

                User assignee = user.get();
                Task currentTask = task.get();
                MonthlyGoal monthlyGoal1 = monthlyGoal.get();

                currentTask.setName(request.getName());
                currentTask.setDescription(request.getDescription());
                currentTask.setCompletionDate(request.getCompletionDate());
                currentTask.setOverdue(request.getOverdue());
                currentTask.setAssignedTo(assignee);
                currentTask.setMonthlyGoal(monthlyGoal1);

                return taskRepository.save(currentTask);

            } else{
                return null;
            }
        } catch (Exception e){
            throw e;
        }
    }
}
