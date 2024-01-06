package com.UserModel.UserModel.MonthlyGoal;

import com.UserModel.UserModel.QuarterlyGoal.QuarterlyGoal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "monthly_goals")
public class MonthlyGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mt_Description;
    private String mt_Month;
    private String mt_Objective;
    private String mt_Result;
    private String mt_Target_Completion_Date;
    private Long priority_ID;
    private String mt_Status;
    private String mt_Progress_Update;
    private String mt_Feedback;
    private Long rating_ID;
    private String mt_Approval_Status;
    private String mt_Completion_Date;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "qt_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private QuarterlyGoal quarterlyGoal;
}
