package com.UserModel.UserModel.QuarterlyGoal;

import com.UserModel.UserModel.YearlyGoal.YearlyGoal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "quarterly-goals")
public class QuarterlyGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String qt_Description;
    private String qt_Period;
    private String qt_Objective;
    private String qt_Result;
    private String qt_Target_Completion_Date;
    private Long priority_ID;
    private String qt_Status;
    private String qt_Progress_Update;
    private String qt_Feedback;
    private Long rating_ID;
    private String qt_Approval_Status;
    private String qt_Completion_Date;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "yg_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private YearlyGoal yearlyGoal;

}
