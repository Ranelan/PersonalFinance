package za.ac.cput.controller;

import org.junit.jupiter.api.*;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
        import za.ac.cput.domain.Admin;
import za.ac.cput.repository.AdminRepository;
import za.ac.cput.service.AdminService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminControllerTest {

    private Admin admin;
    @Autowired
    private AdminService adminService;


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/admin";
    }

    @BeforeEach
    void setUp() {
        admin = new Admin.AdminBuilder()
                .setUserName("Scelo Nyandeni")
                .setEmail("nyandeni@example.com")
                .setPassword("securePassword123")
                .setAdminCode("ADMIN123")
                .build();

        admin = adminService.create(admin);
        assertNotNull(admin.getUserID(), "Admin ID should be generated after save");
    }

    @Test
    @Order(1)
    void testCreateAdmin() {
        ResponseEntity<Admin> response = restTemplate.postForEntity(getBaseUrl() + "/create", admin, Admin.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUserID());
        admin = response.getBody();
    }

    @Test
    @Order(2)
    void testReadAdmin() {
        ResponseEntity<Admin> response = restTemplate.getForEntity(getBaseUrl() + "/read/" + admin.getUserID(), Admin.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(admin.getUserName(), response.getBody().getUserName());
    }

    @Test
    @Order(3)
    void testUpdateAdmin() {
        Admin updatedAdmin = new Admin.AdminBuilder()
                .copy(admin)
                .setUserName("Updated Scelo")
                .build();

        HttpEntity<Admin> request = new HttpEntity<>(updatedAdmin);
        ResponseEntity<Admin> response = restTemplate.exchange(getBaseUrl() + "/update", HttpMethod.PUT, request, Admin.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Scelo", response.getBody().getUserName());

        admin = response.getBody();
    }

    @Test
    @Order(4)
    void testFindByUserName() {
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(getBaseUrl() + "/findByUserName/" + admin.getUserName(), Admin[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Admin[] admins = response.getBody();
        assertNotNull(admins);
        assertTrue(admins.length > 0);
        assertEquals(admin.getUserName(), admins[0].getUserName());
    }

    @Test
    @Order(5)
    void testLogIn() {
        var loginRequest = new Object() {
            public String usernameOrEmail = admin.getEmail();
            public String password = admin.getPassword();
        };

        HttpEntity<Object> request = new HttpEntity<>(loginRequest);
        ResponseEntity<Admin> response = restTemplate.postForEntity(getBaseUrl() + "/login", request, Admin.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(admin.getEmail(), response.getBody().getEmail());
    }

    @Test
    @Order(6)
    void testDeleteAdmin() {
        restTemplate.delete(getBaseUrl() + "/delete/" + admin.getUserID());

        ResponseEntity<Admin> response = restTemplate.getForEntity(getBaseUrl() + "/read/" + admin.getUserID(), Admin.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(8)
    void testFindByEmail() {
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(getBaseUrl() + "/findByEmail/" + admin.getEmail(), Admin[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @Order(9)
    void testFindAll() {
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(getBaseUrl() + "/findAll", Admin[].class);
        assertTrue(response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(10)
    void testViewAllRegularUsers() {
        ResponseEntity<Object[]> response = restTemplate.getForEntity(getBaseUrl() + "/regular-users", Object[].class);
        assertTrue(response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(11)
    void testViewAnonymizedAnalytics() {
        ResponseEntity<Void> response = restTemplate.getForEntity(getBaseUrl() + "/analytics", Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Category management tests (short)
    @Test
    @Order(12)
    void testCreateCategory() {
        String url = getBaseUrl() + "/categories/create?name=TestCategory&type=Expense";
        ResponseEntity<Object> response = restTemplate.postForEntity(url, null, Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(13)
    void testViewAllCategories() {
        ResponseEntity<Object[]> response = restTemplate.getForEntity(getBaseUrl() + "/categories/all", Object[].class);
        assertTrue(response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    }
