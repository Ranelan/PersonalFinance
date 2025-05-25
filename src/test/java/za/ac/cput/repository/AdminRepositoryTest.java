/*
 * AdminRepositoryTest.java
 * Goal Test Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 25 May 2025*/

package za.ac.cput.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Admin;
import za.ac.cput.factory.AdminFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AdminRepositoryTest {


    @Autowired
    private AdminRepository adminRepository;

    // Static admin to ensure persistence across test methods
    private static Admin admin = AdminFactory.createAdmin(
            null, "Scelo Kevin",
            "scelo@email.com", "password123",
            "ADMIN123");

    @Test
    public void testCreateAdmin() {
        Admin savedAdmin = adminRepository.save(admin);
        assertNotNull(savedAdmin.getUserID());
        assertEquals(admin.getUserName(), savedAdmin.getUserName());
        assertEquals(admin.getEmail(), savedAdmin.getEmail());
        assertEquals(admin.getPassword(), savedAdmin.getPassword());
        assertEquals(admin.getAdminCode(), savedAdmin.getAdminCode());
    }

    @Test
    public void testFindAdminByID() {
        Admin savedAdmin = adminRepository.save(admin);
        Optional<Admin> foundAdmin = adminRepository.findById(savedAdmin.getUserID());
        assertTrue(foundAdmin.isPresent());
        assertEquals(savedAdmin.getUserName(), foundAdmin.get().getUserName());
        assertEquals(savedAdmin.getEmail(), foundAdmin.get().getEmail());
        assertEquals(savedAdmin.getPassword(), foundAdmin.get().getPassword());
        assertEquals(savedAdmin.getAdminCode(), foundAdmin.get().getAdminCode());
    }

    @Test
    public void testFindAdminByEmail(){
        Admin savedAdmin = adminRepository.save(admin);

        List<Admin> foundAdmins = adminRepository.findByEmail(savedAdmin.getEmail());

        assertFalse(foundAdmins.isEmpty());

        Admin foundAdmin = foundAdmins.get(0);

        assertEquals(savedAdmin.getUserName(), foundAdmin.getUserName());
        assertEquals(savedAdmin.getEmail(), foundAdmin.getEmail());
        assertEquals(savedAdmin.getPassword(), foundAdmin.getPassword());
    }

    //test case to find by password
    @Test
    public void testFindAdminByPassword(){
        Admin savedAdmin = adminRepository.save(admin);

        List<Admin> foundAdmins = adminRepository
                .findByPassword(savedAdmin.getPassword());

        assertFalse(foundAdmins.isEmpty());

        Admin foundAdmin = foundAdmins.get(0);

        assertEquals(savedAdmin.getUserName(), foundAdmin.getUserName());
        assertEquals(savedAdmin.getEmail(), foundAdmin.getEmail());
        assertEquals(savedAdmin.getPassword(), foundAdmin.getPassword());
    }

    @Test
    public void testUpdateAdmin() {
        Admin savedAdmin = adminRepository.save(admin);

        Admin updatedAdmin = new Admin.AdminBuilder()
                .setUserID(savedAdmin.getUserID())   // very important to identify the existing record
                .setUserName("Scelo Kevin")
                .setEmail("scelo@email.com")
                .setPassword("password123")
                .setAdminCode("ADMIN123")
                .build();

        Admin result = adminRepository.save(updatedAdmin);

        assertEquals("Scelo Kevin", result.getUserName());
        assertEquals("scelo@email.com", result.getEmail());
        assertEquals("password123", result.getPassword());
        assertEquals("ADMIN123", result.getAdminCode());
    }

}
