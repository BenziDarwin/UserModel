package com.UserModel.UserModel.task;

import com.UserModel.UserModel.MonthlyGoal.MonthlyGoal;
import com.UserModel.UserModel.QuarterlyGoal.QuarterlyGoal;
import com.UserModel.UserModel.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Date completionDate;
    private Boolean overdue;
    private Boolean completed;
    private String reason;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User assignedTo;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "mt_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private MonthlyGoal monthlyGoal;
}
