/* Goal.java
     Goal POJO class
     Author: Ranelani Engel(221813853
     Date: 11 May 2025 */

package za.ac.cput.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goalId;
    private String goalName;
    private double targetAmount;
    private double currentAmount;
    private LocalDate deadLine;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userID")
    private RegularUser regularUser;

    public Goal() {
    }

    public Goal(Long goalId, String goalName, double targetAmount, LocalDate deadLine, double currentAmount, RegularUser regularUser) {
        this.goalId = goalId;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.deadLine = deadLine;
        this.currentAmount = currentAmount;
        this.regularUser = regularUser;
    }

    public Goal(GoalBuilder goalBuilder) {
        this.goalId = goalBuilder.goalId;
        this.goalName = goalBuilder.goalName;
        this.targetAmount = goalBuilder.targetAmount;
        this.currentAmount = goalBuilder.currentAmount;
        this.deadLine = goalBuilder.Deadline;
        this.regularUser = goalBuilder.regularUser;
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

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public RegularUser getRegularUser() {
        return regularUser;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "goalId=" + goalId +
                ", goalName='" + goalName + '\'' +
                ", targetAmount=" + targetAmount +
                ", currentAmount=" + currentAmount +
                ", Deadline=" + deadLine +
                ", regularUser=" + regularUser +
                '}';
    }

    public static class GoalBuilder{
        private Long goalId;
        private String goalName;
        private double targetAmount;
        private double currentAmount;
        private LocalDate Deadline;
        private RegularUser regularUser;

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
            this.Deadline = deadline;
            return this;
        }
        public GoalBuilder setRegularUser(RegularUser regularUser) {
            this.regularUser = regularUser;
            return this;
        }

        public GoalBuilder copy(Goal goal) {
            this.goalId = goal.goalId;
            this.goalName = goal.goalName;
            this.targetAmount = goal.targetAmount;
            this.currentAmount = goal.currentAmount;
            this.Deadline = goal.deadLine;
            this.regularUser = goal.regularUser;
            return this;
        }

        public Goal build() {
            return new Goal(this);
        }
    }
}
