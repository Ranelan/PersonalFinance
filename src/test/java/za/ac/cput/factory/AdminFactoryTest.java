/*
 * AdminFactoryTest.java
 * Goal Test Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 18 May 2025*/
package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminFactoryTest {
    @Test
    public void testCreateAdmin() {
        // Create a valid Admin
        Admin admin = new Admin.AdminBuilder()
                .setUserID(1L)
                .setUserName("Scelo Kevin")
                .setEmail("scelo@email.com")
                .setPassword("password123")
                .setAdminCode("ADMIN123")
                .build();


        assertNotNull(admin);
        assertEquals(1L, admin.getUserID());
        assertEquals("Scelo Kevin", admin.getUserName());
        assertEquals("password123", admin.getPassword());
        assertEquals("ADMIN123", admin.getAdminCode());

    }

    @Test
    void testAdminWithNullValues() {
        // Create an Admin with null values
        Admin admin = new Admin.AdminBuilder()
                .setUserID(null)
                .setUserName(null)
                .setEmail(null)
                .setPassword(null)
                .setAdminCode(null)
                .build();

        assertNotNull(admin);
        assertEquals(null, admin.getUserID());
        assertEquals(null, admin.getUserName());
        assertEquals(null, admin.getPassword());
        assertEquals(null, admin.getAdminCode());
    }

    @Test
    void testAdminWithEmptyStrings() {
        // Create an Admin with empty strings
        Admin admin = new Admin.AdminBuilder()
                .setUserID(1L)
                .setUserName("")
                .setEmail("")
                .setPassword("")
                .setAdminCode("")
                .build();

        assertNotNull(admin);
        assertEquals(1L, admin.getUserID());
        assertEquals("", admin.getUserName());
        assertEquals("", admin.getPassword());
        assertEquals("", admin.getAdminCode());
    }

    @Test
    void testAdminWithInvalidEmailFormat() {
        // Create an Admin with invalid email format
        Admin admin = new Admin.AdminBuilder()
                .setUserID(1L)
                .setUserName("Scelo Kevin")
                .setEmail("invalid-email-format")
                .setPassword("password123")
                .setAdminCode("ADMIN123")
                .build();

        assertNotNull(admin);
        assertEquals(1L, admin.getUserID());
        assertEquals("Scelo Kevin", admin.getUserName());
        assertEquals("invalid-email-format", admin.getEmail());
        assertEquals("password123", admin.getPassword());
        assertEquals("ADMIN123", admin.getAdminCode());
    }
}
