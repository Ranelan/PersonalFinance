/*
 * RegularUserFactoryTest.java
 * Goal Test Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 18 May 2025*/

package za.ac.cput.factory;

import org.junit.jupiter.api.Test;
import za.ac.cput.domain.RegularUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegularUserFactoryTest {

    // Test case for RegularUserFactor

    @Test
    public void testCreateRegularUser() {
        // Create a valid RegularUser
        RegularUser regularUser = new RegularUser.RegularUserBuilder()
                .setUserID(1L)
                .setUserName("Raney Gee")
                .setEmail("raney@emaill.com")
                .setPassword("password133")
                .setMembershipID("MEMB123")
                .build();

        assertNotNull(regularUser);
        assertEquals(1L, regularUser.getUserID());
        assertEquals("Raney Gee", regularUser.getUserName());
        assertEquals("raney@emaill.com", regularUser.getEmail());
        assertEquals("password133", regularUser.getPassword());
        assertEquals("MEMB123", regularUser.getMembershipID());

    }

    @Test
    void testRegularUserWithNullValues() {
        // Create a RegularUser with null values
        RegularUser regularUser = new RegularUser.RegularUserBuilder()
                .setUserID(null)
                .setUserName(null)
                .setEmail(null)
                .setPassword(null)
                .setMembershipID(null)
                .build();

        assertNotNull(regularUser);
        assertEquals(null, regularUser.getUserID());
        assertEquals(null, regularUser.getUserName());
        assertEquals(null, regularUser.getEmail());
        assertEquals(null, regularUser.getPassword());
        assertEquals(null, regularUser.getMembershipID());
    }

    @Test
    void testRegularUserWithEmptyStrings() {
        // Create a RegularUser with empty strings
        RegularUser regularUser = new RegularUser.RegularUserBuilder()
                .setUserID(1L)
                .setUserName("")
                .setEmail("")
                .setPassword("")
                .setMembershipID("")
                .build();

        assertNotNull(regularUser);
        assertEquals(1L, regularUser.getUserID());
        assertEquals("", regularUser.getUserName());
        assertEquals("", regularUser.getEmail());
        assertEquals("", regularUser.getPassword());
        assertEquals("", regularUser.getMembershipID());
    }

    @Test
    void testRegularUserWithInvalidEmailFormat() {
        // Create a RegularUser with invalid email format
        RegularUser regularUser = new RegularUser.RegularUserBuilder()
                .setUserID(1L)
                .setUserName("Raney Gee")
                .setEmail("invalid-email")
                .setPassword("password133")
                .setMembershipID("MEMB123")
                .build();

        assertNotNull(regularUser);
        assertEquals(1L, regularUser.getUserID());
        assertEquals("Raney Gee", regularUser.getUserName());
        assertEquals("invalid-email", regularUser.getEmail());
        assertEquals("password133", regularUser.getPassword());
        assertEquals("MEMB123", regularUser.getMembershipID());
    }
}
