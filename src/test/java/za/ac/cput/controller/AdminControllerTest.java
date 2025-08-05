/*

package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.ac.cput.domain.Admin;
import za.ac.cput.factory.AdminFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminControllerTest {

    private Admin admin;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/admin";
    }

    @BeforeEach
    void setUp() {
        admin = AdminFactory.createAdmin(
                null, "Scelo Kevin", "scelo@email.com", "password123", "ADMIN123");

        String url = getBaseUrl() + "/create";
        ResponseEntity<Admin> response = restTemplate.postForEntity(url, admin, Admin.class);
        assertEquals(200, response.getStatusCodeValue(), "Setup create admin failed");
        admin = response.getBody();
        assertNotNull(admin);
        assertNotNull(admin.getUserID());
    }

    @Test
    @Order(1)
    void testCreateAdmin() {
        assertNotNull(admin);
        assertNotNull(admin.getUserID());
    }

    @Test
    @Order(2)
    void testReadAdmin() {
        String url = getBaseUrl() + "/read/" + admin.getUserID();
        ResponseEntity<Admin> response = restTemplate.getForEntity(url, Admin.class);
        assertEquals(200, response.getStatusCodeValue(), "Read admin failed");
        Admin foundAdmin = response.getBody();
        assertNotNull(foundAdmin, "Found admin should not be null");
        assertEquals(admin.getUserID(), foundAdmin.getUserID(), "Admin IDs should match");
    }

    @Test
    @Order(3)
    void testUpdateAdmin() {
        Admin updatedAdmin = new Admin.AdminBuilder()
                .setUserID(admin.getUserID())
                .setUserName("Updated Name")
                .setEmail(admin.getEmail())
                .setPassword(admin.getPassword())
                .setAdminCode(admin.getAdminCode())
                .build();

        String url = getBaseUrl() + "/update";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Admin> entity = new HttpEntity<>(updatedAdmin, headers);

        ResponseEntity<Admin> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                Admin.class
        );

        assertEquals(200, response.getStatusCodeValue(), "Update admin failed");

        Admin updated = response.getBody();
        assertNotNull(updated, "Updated admin should not be null");
        assertEquals("Updated Name", updated.getUserName(), "UserName should be updated");

        admin = updated;
    }

    @Test
    @Order(4)
    void testFindByUserName() {
        String url = getBaseUrl() + "/findByUserName/" + admin.getUserName();
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(url, Admin[].class);
        assertEquals(200, response.getStatusCodeValue(), "Find by userName failed");

        Admin[] foundAdmins = response.getBody();
        assertNotNull(foundAdmins, "Found admins should not be null");
        assertTrue(foundAdmins.length > 0, "Should find at least one admin for the userName");

        for (Admin a : foundAdmins) {
            assertEquals(admin.getUserName(), a.getUserName(), "UserName should match");
        }
    }

    @Test
    @Order(5)
    void testFindByEmail() {
        String url = getBaseUrl() + "/findByEmail/" + admin.getEmail();
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(url, Admin[].class);
        assertEquals(200, response.getStatusCodeValue(), "Find by email failed");

        Admin[] foundAdmins = response.getBody();
        assertNotNull(foundAdmins, "Found admins should not be null");
        assertTrue(foundAdmins.length > 0, "Should find at least one admin for the email");

        for (Admin a : foundAdmins) {
            assertEquals(admin.getEmail(), a.getEmail(), "Email should match");
        }
    }

    @Test
    @Order(6)
    void testFindAll() {
        String url = getBaseUrl() + "/findAll";
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(url, Admin[].class);
        assertEquals(200, response.getStatusCodeValue(), "Find all admins failed");

        Admin[] allAdmins = response.getBody();
        assertNotNull(allAdmins, "List of all admins should not be null");
        assertTrue(allAdmins.length > 0, "Should find at least one admin");

        boolean found = false;
        for (Admin a : allAdmins) {
            if (a.getUserID().equals(admin.getUserID())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Created admin should be in the list of all admins");
    }

    @Test
    @Order(7)
    void testDeleteAdmin() {
        String url = getBaseUrl() + "/delete/" + admin.getUserID();
        restTemplate.delete(url);

        String readUrl = getBaseUrl() + "/read/" + admin.getUserID();
        ResponseEntity<Admin> response = restTemplate.getForEntity(readUrl, Admin.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

 */