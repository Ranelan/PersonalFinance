/*
 * UserFactoryTest.java
 * Goal Test Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 18 May 2025*/
package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserFactoryTest {
    @Test
    public void testCreateUser() {
        // Create a valid User
        User user = new User.UserBuilder()
                .setUserID(1L)
                .setUserName("Scelo Kevin")
                .setEmail("scelo@email.com")
                .setPassword("password123")
                .build();

        assertNotNull(user);
        assertEquals(1L, user.getUserID());
        assertEquals("Scelo Kevin", user.getUserName());
        assertEquals("scelo@email.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testUserWithNullValues() {
        // Create a User with null values
        User user = new User.UserBuilder()
                .setUserID(null)
                .setUserName(null)
                .setEmail(null)
                .setPassword(null)
                .build();

        assertNotNull(user);
        assertEquals(null, user.getUserID());
        assertEquals(null, user.getUserName());
        assertEquals(null, user.getEmail());
        assertEquals(null, user.getPassword());
    }

    @Test
    void testUserWithEmptyStrings() {
        // Create a User with empty strings
        User user = new User.UserBuilder()
                .setUserID(1L)
                .setUserName("")
                .setEmail("")
                .setPassword("")
                .build();

        assertNotNull(user);
        assertEquals(1L, user.getUserID());
        assertEquals("", user.getUserName());
        assertEquals("", user.getEmail());
        assertEquals("", user.getPassword());
    }

    @Test
    void testUserWithInvalidEmailFormat() {
        // Create a User with invalid email format
        User user = new User.UserBuilder()
                .setUserID(1L)
                .setUserName("Scelo Kevin")
                .setEmail("invalid-email-format")
                .setPassword("password123")
                .build();

        assertNotNull(user);
        assertEquals(1L, user.getUserID());
        assertEquals("Scelo Kevin", user.getUserName());
        assertEquals("invalid-email-format", user.getEmail());
        assertEquals("password123", user.getPassword());
    }
}
