package com.UserModel.UserModel.task;

import com.UserModel.UserModel.MonthlyGoal.MonthlyGoal;
import com.UserModel.UserModel.MonthlyGoal.MonthlyGoalRepository;
import com.UserModel.UserModel.User.User;
import com.UserModel.UserModel.User.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MonthlyGoalRepository monthlyGoalRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Task createTask(Task request, Long userId, Long mtID) {

        Optional<User> user = usersRepository.findById(userId);
        Optional<MonthlyGoal> monthlyGoal = monthlyGoalRepository.findById(mtID);

        if (user.isPresent() && monthlyGoal.isPresent()) {
            User assignee = user.get();
            Task task = Task.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .completionDate(request.getCompletionDate())
                    .overdue(request.getOverdue())
                    .assignedTo(assignee)
                    .monthlyGoal(monthlyGoal.get())
                    .completed((request.getCompleted()))
                    .approval(request.getApproval())
                    .feedback(request.getFeedback())
                    .objective(request.getObjective())
                    .reason(request.getReason())
                    .status(request.getStatus())
                    .build();

            return taskRepository.save(task);

        } else {
            return null;
        }

    }

    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    public Object deleteTask(Long taskID) {
        Map<String, Object> response = new HashMap<>();
        taskRepository.deleteById(taskID);
        response.put("success", true);
        return response;
    }

    public Task updateTask(Task request, Long assignedTo, Long mtID) {
        Optional<User> user = usersRepository.findById(assignedTo);
        Optional<Task> task = taskRepository.findById(request.getId());
        Optional<MonthlyGoal> monthlyGoal = monthlyGoalRepository.findById(mtID);

        if (user.isPresent() && task.isPresent() && monthlyGoal.isPresent()) {

            User assignee = user.get();
            Task currentTask = task.get();
            MonthlyGoal monthlyGoal1 = monthlyGoal.get();

            currentTask.setName(request.getName());
            currentTask.setDescription(request.getDescription());
            currentTask.setCompletionDate(request.getCompletionDate());
            currentTask.setOverdue(request.getOverdue());
            currentTask.setCompleted(request.getCompleted());
            currentTask.setReason(request.getReason());
            currentTask.setObjective(request.getObjective());
            currentTask.setApproval(request.getApproval());
            currentTask.setFeedback(request.getFeedback());
            currentTask.setStatus(request.getStatus());
            currentTask.setAssignedTo(assignee);
            currentTask.setMonthlyGoal(monthlyGoal1);
            return taskRepository.save(currentTask);

        } else {
            return null;
        }
    }

    public Task completeTask(Task request, Long taskID, Long assignedTo) {

        Optional<Task> task = taskRepository.findById(taskID);
        Optional<User> user = usersRepository.findById(assignedTo);

        if (task.isPresent() && user.isPresent()) {
            Task currentTask = task.get();
            currentTask.setCompleted(request.getCompleted());
            currentTask.setName(request.getName());
            currentTask.setDescription(request.getDescription());
            currentTask.setCompletionDate(request.getCompletionDate());
            currentTask.setOverdue(request.getOverdue());
            currentTask.setCompleted(request.getCompleted());
            currentTask.setReason(request.getReason());
            currentTask.setObjective(request.getObjective());
            currentTask.setApproval(request.getApproval());
            currentTask.setFeedback(request.getFeedback());
            currentTask.setStatus(request.getStatus());
            currentTask.setAssignedTo(user.get());
            currentTask.setMonthlyGoal(currentTask.getMonthlyGoal());

            Task savedTask = taskRepository.save(currentTask);

            updateGoalStatus(savedTask.getMonthlyGoal().getId());

            return savedTask;

        } else {
            return null;
        }
    }

    @Transactional
    private void updateGoalStatus(Long monthlyGoalId) {
        try {
            taskRepository.completeGoal(monthlyGoalId);
        } catch (Exception e) {
            logger.error("Error updating goal status", e);
            throw e;
        }
    }

    private void updateQuarterlyGoalStatus(Long quarterlyGoalId) {
        Query query = entityManager.createQuery(
                "UPDATE QuarterlyGoal q " +
                        "SET q.qt_Status = CASE WHEN (SELECT COUNT(m) FROM MonthlyGoal m WHERE m.quarterlyGoal.id = :quarterlyGoalId AND m.mt_Status = 'incomplete') = 0 THEN 'complete' ELSE 'incomplete' END " +
                        "WHERE q.id = :quarterlyGoalId"
        );
        query.setParameter("quarterlyGoalId", quarterlyGoalId);
        query.executeUpdate();
    }
}
