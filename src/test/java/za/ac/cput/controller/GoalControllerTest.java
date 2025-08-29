package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.ac.cput.domain.Goal;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;
import za.ac.cput.factory.GoalFactory;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.repository.RoleRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GoalControllerTest {

    private Goal goal;
    private User user;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/goal";
    }

    @BeforeAll
    void init() {
        Role role = roleRepository.findByName("REGULAR_USER").orElseGet(() -> roleRepository.save(new Role("REGULAR_USER")));

        user = UserFactory.createUser(
                "Ranelani Engel",
                "engel@example.com",
                "securePassword123!",
                role,
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>()
        );
        user = userRepository.save(user);

        goal = GoalFactory.createGoal(
                "Save for registration",
                10000.00,
                5000.00,
                LocalDate.of(2026, 1, 10),
                user
        );

        String url = getBaseUrl() + "/create";
        ResponseEntity<Goal> response = restTemplate.postForEntity(url, goal, Goal.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Goal creation failed. Status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());
        }
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
