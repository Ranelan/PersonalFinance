package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.ac.cput.domain.Goal;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.factory.GoalFactory;
import za.ac.cput.factory.RegularUserFactory;
import za.ac.cput.repository.RegularUserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GoalControllerTest {

    private Goal goal;
    private RegularUser regularUser;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RegularUserRepository regularUserRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/goal";
    }

    @BeforeAll
    void init() {

        regularUser = RegularUserFactory.createRegularUser("Ranelani Engel", "engel@example.com", "securePassword123");
        regularUser = regularUserRepository.save(regularUser);

        goal = GoalFactory.createGoal(
                "Save for registration",
                10000.00,
                5000.00,
                LocalDate.of(2026, 1, 10),
                regularUser
        );

        String url = getBaseUrl() + "/create";
        ResponseEntity<Goal> response = restTemplate.postForEntity(url, goal, Goal.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Setup create goal failed");
        goal = response.getBody();
        assertNotNull(goal);
        assertNotNull(goal.getGoalId());
    }

    @Test
    void a_create() {
        assertNotNull(goal);
        assertNotNull(goal.getGoalId());
    }

    @Test
    void b_read() {
        String url = getBaseUrl() + "/read/" + goal.getGoalId();
        ResponseEntity<Goal> response = restTemplate.getForEntity(url, Goal.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(goal.getGoalId(), response.getBody().getGoalId());
    }

    @Test
    void c_update() {
        Goal updatedGoal = new Goal.GoalBuilder()
                .copy(goal)
                .setCurrentAmount(8000.00)
                .build();

        String url = getBaseUrl() + "/update";

        HttpEntity<Goal> request = new HttpEntity<>(updatedGoal);
        ResponseEntity<Goal> response = restTemplate.exchange(url, HttpMethod.PUT, request, Goal.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(8000.00, response.getBody().getCurrentAmount());

        goal = response.getBody();
    }

    @Test
    void d_findByGoalName() {
        String url = getBaseUrl() + "/findByGoalName/" + goal.getGoalName();
        ResponseEntity<Goal[]> response = restTemplate.getForEntity(url, Goal[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void e_findByDeadLine() {
        String url = getBaseUrl() + "/findByDeadLine/" + goal.getDeadLine();
        ResponseEntity<Goal[]> response = restTemplate.getForEntity(url, Goal[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void f_findAll() {
        String url = getBaseUrl() + "/findAll";
        ResponseEntity<Goal[]> response = restTemplate.getForEntity(url, Goal[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void g_delete() {
        String url = getBaseUrl() + "/delete/" + goal.getGoalId();
        restTemplate.delete(url);


        String readUrl = getBaseUrl() + "/read/" + goal.getGoalId();
        ResponseEntity<Goal> response = restTemplate.getForEntity(readUrl, Goal.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
