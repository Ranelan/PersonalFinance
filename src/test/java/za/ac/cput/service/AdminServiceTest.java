package za.ac.cput.service;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.repository.AdminRepository;
import za.ac.cput.repository.RegularUserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

@Autowired
    private AdminRepository adminRepository;



    private Admin admin;

    @BeforeEach
    void setUpAdmin() {
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
    void create() {
        Admin newAdmin = new Admin.AdminBuilder()
                .setUserName("Ranelani Engel")
                .setEmail("ranelani@example")
                .setPassword("securePassword123")
                .setAdminCode("ADMIN123")
                .build();

        Admin createdAdmin = adminService.create(newAdmin);
        assertNotNull(createdAdmin, "Created admin should not be null");
        assertNotNull(createdAdmin.getUserID(), "Admin ID should be generated");
        }

    @Test
    @Order(2)
    void read() {
        Admin foundAdmin = adminService.read(admin.getUserID());
        assertNotNull(foundAdmin, "Found admin should not be null");
        assertNotNull(foundAdmin.getUserID(), "Admin ID should be generated");

    }
@Test
    @Order(3)
    void logIn() {
        Admin admin = new Admin.AdminBuilder()
                .setUserName("New Admin")
                .setEmail("newAdmin@gmail.com")
                .setPassword("newPassword123")
                .build();

        adminRepository.save(admin);

        Admin loggedInAdmin = adminService.logIn("newAdmin@gmail.com", "newPassword123");
        assertNotNull(loggedInAdmin, "Logged in admin should not be null");
        //assertEquals("New User", loggedInUser.getUserName(), "User name should match");
    }

    @Test
    @Order(4)
    void update(){
        Admin updatedAdmin = new Admin.AdminBuilder()
                .copy(admin)
                .setUserName("Ranelani Engel")
                .setEmail("ranelani@example")
                .setPassword("securePassword123")
                .setAdminCode("ADMIN123")
                .build();

        Admin updated = adminService.update(updatedAdmin);
        assertNotNull(updated, "Updated admin should not be null");
        assertNotNull(updated.getUserID(), "Admin ID should be generated");

    }
    @Test
    @Order(5)
    void delete(){
        adminService.delete(admin.getUserID());
        Admin deletedAdmin = adminService.read(admin.getUserID());
        assertNull(deletedAdmin, "Deleted admin should not be null");
    }

    @Test
    @Order(6)
    void findByUserName(){
        List<Admin> admins = adminService.findByUserName("Ranelani Engel");
        assertFalse(admins.isEmpty(), "Admin should not be empty");

        Admin admin = admins.get(0);
        assertEquals("Ranelani Engel", admin.getUserName());

        for (Admin a : admins) {
            assertEquals("Ranelani Engel", a.getUserName());
        }
    }

    @Test
    @Order(7)
    void findByEmail(){
        List<Admin> admins = adminService.findByEmail("ranelani@example");
        assertFalse(admins.isEmpty(), "Admin should not be empty");

        Admin admin = admins.get(0);
        assertEquals("ranelani@example", admin.getEmail());

        for (Admin a : admins) {
            assertEquals("ranelani@example", a.getEmail());
        }
    }

    @Test
    @Order(8)
    void findAll(){
        Iterable<Admin> admins = adminService.findAll();
        assertNotNull(admins, "Admins should not be null");
        assertTrue(admins.iterator().hasNext(), "There should be at least one admin");
        assertTrue(admins.spliterator().getExactSizeIfKnown() > 0, "There should be more than zero admins");
    }


}
