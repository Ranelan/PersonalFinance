/* Goal.java
     Goal POJO class
     Author: Ranelani Engel(221813853
     Date: 11 May 2025 */

package za.ac.cput.domain;

import java.time.LocalDate;

public class Goal {
    private Long goalId;
    private String goalName;
    private Double targetAmount;
    private LocalDate Deadline;

    public Goal() {
    }

    public Goal(Long goalId, String goalName, Double targetAmount, LocalDate deadline) {
        this.goalId = goalId;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        Deadline = deadline;
    }

    public Goal(GoalBuilder goalBuilder) {
        this.goalId = goalBuilder.goalId;
        this.goalName = goalBuilder.goalName;
        this.targetAmount = goalBuilder.targetAmount;
        this.Deadline = goalBuilder.Deadline;
    }

    public Long getGoalId() {
        return goalId;
    }

    public String getGoalName() {
        return goalName;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public LocalDate getDeadline() {
        return Deadline;
    }

    public static class GoalBuilder{
        private Long goalId;
        private String goalName;
        private Double targetAmount;
        private LocalDate Deadline;

        public GoalBuilder setGoalId(Long goalId) {
            this.goalId = goalId;
            return this;
        }

        public GoalBuilder setGoalName(String goalName) {
            this.goalName = goalName;
            return this;
        }

        public GoalBuilder setTargetAmount(Double targetAmount) {
            this.targetAmount = targetAmount;
            return this;
        }

        public GoalBuilder setDeadline(LocalDate deadline) {
            Deadline = deadline;
            return this;
        }

        public GoalBuilder copy(Goal goal) {
            this.goalId = goal.goalId;
            this.goalName = goal.goalName;
            this.targetAmount = goal.targetAmount;
            this.Deadline = goal.Deadline;
            return this;
        }

        public Goal build() {
            return new Goal(this);
        }
    }
}
