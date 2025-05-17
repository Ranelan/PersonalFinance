/* Goal.java
     Goal POJO class
     Author: Ranelani Engel(221813853
     Date: 11 May 2025 */

package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goalId;
    private String goalName;
    private double targetAmount;
    private double currentAmount;
    private LocalDate Deadline;

    public Goal() {
    }

    public Goal(Long goalId, String goalName, double targetAmount, LocalDate deadline) {
        this.goalId = goalId;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        Deadline = deadline;
    }

    public Goal(GoalBuilder goalBuilder) {
        this.goalId = goalBuilder.goalId;
        this.goalName = goalBuilder.goalName;
        this.targetAmount = goalBuilder.targetAmount;
        this.currentAmount = goalBuilder.currentAmount;
        this.Deadline = goalBuilder.Deadline;
    }

    public Long getGoalId() {
        return goalId;
    }

    public String getGoalName() {
        return goalName;
    }

    public double getTargetAmount() {
        return targetAmount;
    }
    public double getCurrentAmount() {
        return currentAmount;
    }

    public LocalDate getDeadline() {
        return Deadline;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "goalId=" + goalId +
                ", goalName='" + goalName + '\'' +
                ", targetAmount=" + targetAmount +
                ", currentAmount=" + currentAmount +
                ", Deadline=" + Deadline +
                '}';
    }

    public static class GoalBuilder{
        private Long goalId;
        private String goalName;
        private double targetAmount;
        private double currentAmount;
        private LocalDate Deadline;

        public GoalBuilder setGoalId(Long goalId) {
            this.goalId = goalId;
            return this;
        }

        public GoalBuilder setGoalName(String goalName) {
            this.goalName = goalName;
            return this;
        }

        public GoalBuilder setTargetAmount(double targetAmount) {
            this.targetAmount = targetAmount;
            return this;
        }
        public GoalBuilder setCurrentAmount(double currentAmount) {
            this.currentAmount = currentAmount;
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
            this.currentAmount = goal.currentAmount;
            this.Deadline = goal.Deadline;
            return this;
        }

        public Goal build() {
            return new Goal(this);
        }
    }
}
