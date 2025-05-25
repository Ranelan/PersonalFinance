/*
 * RegularUserRepositoryTest.java
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
import za.ac.cput.domain.RegularUser;
import za.ac.cput.factory.AdminFactory;
import za.ac.cput.factory.RegularUserFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class RegularUserRepositoryTest {

    @Autowired
    private RegularUserRepository regularUserRepository;

    private static RegularUser regularUser = RegularUserFactory.createRegularUser(
            null, "Scelo Kevin",
            "scelo@email.com", "password123",
            "USER123");


    @Test
    public void testCreateRegularUser(){
        RegularUser savedRegularUser = regularUserRepository.save(regularUser);
        assertNotNull(savedRegularUser.getUserID());
        assertEquals(regularUser.getUserName(), savedRegularUser.getUserName());
        assertEquals(regularUser.getEmail(), savedRegularUser.getEmail());
        assertEquals(regularUser.getPassword(), savedRegularUser.getPassword());
        assertEquals(regularUser.getMembershipID(), savedRegularUser.getMembershipID());
    }

    @Test
    public void testFindRegularUserByMembershipID(){
        RegularUser savedRegularUser = regularUserRepository.save(regularUser);

        List<RegularUser> foundUsers = regularUserRepository.findByMembershipID(savedRegularUser.getMembershipID());

        assertFalse(foundUsers.isEmpty());
        RegularUser foundRegularUser = foundUsers.get(0);

        assertEquals(savedRegularUser.getUserName(), foundRegularUser.getUserName());
        assertEquals(savedRegularUser.getEmail(), foundRegularUser.getEmail());
        assertEquals(savedRegularUser.getPassword(), foundRegularUser.getPassword());
        assertEquals(savedRegularUser.getMembershipID(), foundRegularUser.getMembershipID());
    }

    @Test
    public void testFindRegularUserByUserName(){
        RegularUser savedRegularUser = regularUserRepository.save(regularUser);

        List<RegularUser> foundUsers = regularUserRepository.findByUserName(savedRegularUser.getUserName());

        assertFalse(foundUsers.isEmpty());
        RegularUser foundRegularUser = foundUsers.get(0);
        assertEquals(savedRegularUser.getUserName(), foundRegularUser.getUserName());
        assertEquals(savedRegularUser.getEmail(), foundRegularUser.getEmail());
        assertEquals(savedRegularUser.getPassword(), foundRegularUser.getPassword());
        assertEquals(savedRegularUser.getMembershipID(), foundRegularUser.getMembershipID());

    }

    @Test
    public void testFindRegularUserByEmail(){
        RegularUser savedRegularUser = regularUserRepository.save(regularUser);

        List<RegularUser> foundUsers = regularUserRepository.findByEmail(savedRegularUser.getEmail());


        assertFalse(foundUsers.isEmpty());
        RegularUser foundRegularUser = foundUsers.get(0);
        assertEquals(savedRegularUser.getUserName(), foundRegularUser.getUserName());
        assertEquals(savedRegularUser.getEmail(), foundRegularUser.getEmail());
        assertEquals(savedRegularUser.getPassword(), foundRegularUser.getPassword());
        assertEquals(savedRegularUser.getMembershipID(), foundRegularUser.getMembershipID());
    }

    @Test
    public void testFindRegularUserByPassword() {
        RegularUser savedRegularUser = regularUserRepository.save(regularUser);

        List<RegularUser> foundUsers = regularUserRepository.findByPassword(savedRegularUser.getPassword());

        assertFalse(foundUsers.isEmpty());
        RegularUser foundRegularUser = foundUsers.get(0);
        assertEquals(savedRegularUser.getUserName(), foundRegularUser.getUserName());
        assertEquals(savedRegularUser.getEmail(), foundRegularUser.getEmail());
        assertEquals(savedRegularUser.getPassword(), foundRegularUser.getPassword());
        assertEquals(savedRegularUser.getMembershipID(), foundRegularUser.getMembershipID());
    }
}
