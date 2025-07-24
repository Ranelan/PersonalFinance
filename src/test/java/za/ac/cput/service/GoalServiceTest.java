package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Goal;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.repository.RegularUserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GoalServiceTest {

    @Autowired
    private GoalService goalService;

    @Autowired
    private RegularUserRepository regularUserRepository;

    private Goal goal;
    private RegularUser user;

    @BeforeAll
    void setUpUser() {
        // Persist a RegularUser for goal association
        user = new RegularUser.RegularUserBuilder()
                .setUserName("Ranelani Engel")
                .setEmail("ranelani@example.com")
                .setPassword("securePassword123")
                .build();

        user = regularUserRepository.save(user);
        assertNotNull(user.getUserID(), "User ID should be generated after save");
    }

    @Test
    @Order(1)
    void create() {
        goal = new Goal.GoalBuilder()
                .setGoalName("Save for registration")
                .setTargetAmount(10000.00)
                .setCurrentAmount(5000.00)
                .setDeadline(LocalDate.of(2026, 1, 10))
                .setRegularUser(user)
                .build();

        goal = goalService.create(goal);
        assertNotNull(goal, "Goal should not be null after creation");
        assertNotNull(goal.getGoalId(), "Goal ID should be generated");
    }

    @Test
    @Order(2)
    void read() {
        Goal found = goalService.read(goal.getGoalId());
        assertNotNull(found, "Should find goal by ID");
        assertEquals(goal.getGoalId(), found.getGoalId());
    }

    @Test
    @Order(3)
    void update() {
        // Ensure RegularUser is managed before update
        RegularUser managedUser = regularUserRepository.findById(user.getUserID())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Goal updatedGoal = new Goal.GoalBuilder()
                .copy(goal)
                .setCurrentAmount(6000.00)
                .setRegularUser(managedUser)
                .build();

        Goal updated = goalService.update(updatedGoal);
        assertNotNull(updated, "Updated goal should not be null");
        assertEquals(6000.00, updated.getCurrentAmount(), "Updated amount should be 6000.00");

        // Reassign the updated goal for further tests
        goal = updated;
    }

    @Test
    @Order(4)
    void findByGoalId() {
        List<Goal> results = goalService.findByGoalId(goal.getGoalId());
        assertFalse(results.isEmpty(), "Goal should be found by ID");
        assertEquals(goal.getGoalId(), results.get(0).getGoalId());
    }

    @Test
    @Order(5)
    void findByGoalName() {
        List<Goal> results = goalService.findByGoalName(goal.getGoalName());
        assertFalse(results.isEmpty(), "Goal should be found by name");
        assertEquals(goal.getGoalName(), results.get(0).getGoalName());
    }

    @Test
    @Order(6)
    void findByDeadLine() {
        List<Goal> results = goalService.findByDeadLine(goal.getDeadLine().toString());
        assertFalse(results.isEmpty(), "Goal should be found by deadline");
        assertEquals(goal.getDeadLine(), results.get(0).getDeadLine());
    }

    @Test
    @Order(7)
    void findAll() {
        List<Goal> allGoals = goalService.findAll(null);
        assertNotNull(allGoals, "List of all goals should not be null");
        assertTrue(allGoals.stream().anyMatch(g -> g.getGoalId().equals(goal.getGoalId())), "Created goal should be in the list");
    }

    @Test
    @Order(8)
    void delete() {
        goalService.delete(goal.getGoalId());
        Goal deleted = goalService.read(goal.getGoalId());
        assertNull(deleted, "Goal should be null after deletion");
    }
}
