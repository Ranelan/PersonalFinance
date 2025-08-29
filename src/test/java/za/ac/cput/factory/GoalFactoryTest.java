/* GoalFactoryTest.java
 * Test class for GoalFactory
 * Author: Ranelani Engel (221813853)
 * Date: 24 July 2025
 */

package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Goal;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GoalFactoryTest {

    private String goalName;
    private double targetAmount;
    private double currentAmount;
    private LocalDate deadline;
    private User user;

    @BeforeEach
    void setUp() {
        goalName = "Save for registration";
        targetAmount = 10000.00;
        currentAmount = 5000.00;
        deadline = LocalDate.of(2026, 1, 10);

        // Use the factory to create a valid User
        user = UserFactory.createUser(
                "Ranelani",
                "ranelani@example.com",
                "SecurePassword123!",
                new Role("REGULAR_USER"),
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>()
        );
        assertNotNull(user, "UserFactory.createUser returned null");
    }

    @Test
    void createGoalSuccess() {
        Goal goal = GoalFactory.createGoal(goalName, targetAmount, currentAmount, deadline, user);

        assertNotNull(goal);
        assertEquals(goalName, goal.getGoalName());
        assertEquals(targetAmount, goal.getTargetAmount());
        assertEquals(currentAmount, goal.getCurrentAmount());
        assertEquals(deadline, goal.getDeadLine());
        assertEquals(user, goal.getUser());
    }

    @Test
    void createGoalWithInvalidName() {
        Goal goal = GoalFactory.createGoal("", targetAmount, currentAmount, deadline, user);
        assertNull(goal);
    }

    @Test
    void createGoalWithInvalidTargetAmount() {
        Goal goal = GoalFactory.createGoal(goalName, -5000.00, currentAmount, deadline, user);
        assertNull(goal);
    }

    @Test
    void createGoalWithInvalidCurrentAmount() {
        Goal goal = GoalFactory.createGoal(goalName, targetAmount, -3000.00, deadline, user);
        assertNull(goal);
    }

    @Test
    void createGoalWithInvalidDeadline() {
        Goal goal = GoalFactory.createGoal(goalName, targetAmount, currentAmount,
                LocalDate.of(2020, 1, 1), user);
        assertNull(goal);
    }

    @Test
    void createGoalWithNullUser() {
        Goal goal = GoalFactory.createGoal(goalName, targetAmount, currentAmount, deadline, null);
        assertNull(goal);
    }
}
