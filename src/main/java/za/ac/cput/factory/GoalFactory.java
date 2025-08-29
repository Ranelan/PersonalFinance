/* GoalFactory.java
     GoalFactory class
     Author: Ranelani Engel(221813853)
     Date: 17 May 2025 */

package za.ac.cput.factory;

import za.ac.cput.domain.Goal;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

import java.time.LocalDate;

public class GoalFactory {

    public static Goal createGoal(String goalName, double targetAmount, double currentAmount, LocalDate deadLine, User regularUser) {
        if(Helper.isNullOrEmpty(goalName)||
           !Helper.isValidTargetAmount(targetAmount)||
           !Helper.isValidCurrentAmount(currentAmount)||
           !Helper.isValidDeadline(deadLine)||
                regularUser == null
        ) {
            return null;
        }

        return new Goal.GoalBuilder()
                .setUser(regularUser)
                .setGoalName(goalName)
                .setTargetAmount(targetAmount)
                .setCurrentAmount(currentAmount)
                .setDeadline(deadLine)
                .build();

    }
}
