package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.factory.RegularUserFactory;
import za.ac.cput.repository.RegularUserRepository;
import za.ac.cput.service.RegularUserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegularUserControllerTest {

    private RegularUser regularUser;

    @Autowired
    private RegularUserService regularUserService;

    @Autowired
    private RegularUserRepository regularUserRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/regularUser";
    }

    @BeforeEach
    void setUp() {
        regularUser = new RegularUser.RegularUserBuilder()
                .setUserName("Test User")
                .setEmail("testUser@gmail.com")
                .setPassword("password123")
                .build();

        regularUser = regularUserRepository.save(regularUser);
        assertNotNull(regularUser.getUserID(), "User ID should be generated after save");
    }

    @Test
    @Order(1)
    void create() {
        RegularUser newUser = new RegularUser.RegularUserBuilder()
                .setUserName("New User")
                .setEmail("newuser@gmail.com")
                .setPassword("pass123")
                .build();

        ResponseEntity<RegularUser> response =
                restTemplate.postForEntity(getBaseUrl() + "/create", newUser, RegularUser.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUserID());
    }

    @Test
    @Order(2)
    void read() {
        ResponseEntity<RegularUser> response =
                restTemplate.getForEntity(getBaseUrl() + "/read/" + regularUser.getUserID(), RegularUser.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(regularUser.getUserName(), response.getBody().getUserName());
    }

    @Test
    @Order(3)
    void update() {
        RegularUser updated = new RegularUser.RegularUserBuilder()
                .copy(regularUser)
                .setUserName("Updated User")
                .build();

        ResponseEntity<RegularUser> response = restTemplate.exchange(
                getBaseUrl() + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                RegularUser.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated User", response.getBody().getUserName());
    }

    @Test
    @Order(4)
    void findByUserName() {
        ResponseEntity<List> response =
                restTemplate.getForEntity(getBaseUrl() + "/findByUserName/" + regularUser.getUserName(), List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(5)
    void findByEmail() {
        ResponseEntity<List> response =
                restTemplate.getForEntity(getBaseUrl() + "/findByEmail/" + regularUser.getEmail(), List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(6)
    void findAll() {
        ResponseEntity<List> response =
                restTemplate.getForEntity(getBaseUrl() + "/findAll", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(7)
    void loginSuccess() {
        RegularUserController.LoginRequest loginRequest = new RegularUserController.LoginRequest();
        loginRequest.usernameOrEmail = regularUser.getEmail();
        loginRequest.password = regularUser.getPassword();

        ResponseEntity<RegularUser> response = restTemplate.postForEntity(
                getBaseUrl() + "/login",
                loginRequest,
                RegularUser.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(8)
    void loginFailure() {
        RegularUserController.LoginRequest loginRequest = new RegularUserController.LoginRequest();
        loginRequest.usernameOrEmail = "wrong@example.com";
        loginRequest.password = "wrongpass";

        ResponseEntity<RegularUser> response = restTemplate.postForEntity(
                getBaseUrl() + "/login",
                loginRequest,
                RegularUser.class
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @Order(9)
    void delete() {
        restTemplate.delete(getBaseUrl() + "/delete/" + regularUser.getUserID());

        ResponseEntity<RegularUser> response =
                restTemplate.getForEntity(getBaseUrl() + "/read/" + regularUser.getUserID(), RegularUser.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
