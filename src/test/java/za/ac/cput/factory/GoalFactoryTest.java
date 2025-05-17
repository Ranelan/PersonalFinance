package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Goal;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GoalFactoryTest {

    private String goalName;
    private double targetAmount;
    private double currentAmount;
    private LocalDate deadline;

    private GoalFactory goalFactory;
    private Goal goal;


    @BeforeEach
    void setUp() {
        goalName = "Save for registration";
        targetAmount = 10000.00;
        currentAmount = 5000.00;
        deadline = LocalDate.of(2026, 01, 10);
    }

    @Test
    void createGoal() {
        goal = GoalFactory.createGoal(goalName, targetAmount, currentAmount, deadline);
        assertNotNull(goal);
        assertEquals(goalName, goal.getGoalName());
        assertEquals(targetAmount, goal.getTargetAmount());
        assertEquals(currentAmount, goal.getCurrentAmount());
        assertEquals(deadline, goal.getDeadline());
    }

    @Test
    void createGoalWithInvalidName() {
        goal = GoalFactory.createGoal("", targetAmount, currentAmount, deadline);
        assertNull(goal);
    }

    @Test
    void createGoalWithInvalidTargetAmount() {
        goal = GoalFactory.createGoal(goalName, -10000.00, currentAmount, deadline);
        assertNull(goal);
    }

    @Test
    void createGoalWithInvalidCurrentAmount() {
        goal = GoalFactory.createGoal(goalName, targetAmount, -5000.00, deadline);
        assertNull(goal);
    }

    @Test
    void createGoalWithInvalidDeadline() {
        goal = GoalFactory.createGoal(goalName, targetAmount, currentAmount, LocalDate.of(2020, 01, 10));
        assertNull(goal);
    }
}