package com.UserModel.UserModel.YearlyGoal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "yearly_goals")
public class YearlyGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String yg_Description;
    private String yg_Year;
    private String yg_Objective;
    private String yg_Result;
    private String yg_Target_Completion_Date;
    private Long priority_ID;
    private String yg_Status;
    private String yg_Progress_Update;
    private String yg_Feedback;
    private Long rating_ID;
    private String yg_Approval_Status;
    private String yg_Completion_Date;
}
