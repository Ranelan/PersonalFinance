package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.repository.RegularUserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegularUserServiceTest {

    @Autowired
    private RegularUserService regularUserService;

    @Autowired
    private RegularUserRepository regularUserRepository;

    private RegularUser regularUser;

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
                .setEmail("newUser@gmail.com")
                .setPassword("newPassword123")
                .build();

        RegularUser createdUser = regularUserService.create(newUser);
        assertNotNull(createdUser, "Created user should not be null");
        assertNotNull(createdUser.getUserID(), "User ID should be generated");
    }

    @Test
    @Order(2)
    void logIn() {
        RegularUser user = new RegularUser.RegularUserBuilder()
                .setUserName("New User")
                .setEmail("newUser@gmail.com")
                .setPassword("newPassword123")
                .build();

        regularUserRepository.saveAndFlush(user);

        RegularUser loggedInUser = regularUserService.logIn("newUser@gmail.com", "newPassword123");
        assertNotNull(loggedInUser, "Logged in user should not be null");
        //assertEquals("New User", loggedInUser.getUserName(), "User name should match");
    }

    @Test
    @Order(3)
    void read() {
        RegularUser foundUser = regularUserService.read(regularUser.getUserID());
        assertNotNull(foundUser, "Found user should not be null");
        assertEquals(regularUser.getUserID(), foundUser.getUserID(), "User IDs should match");
        assertEquals(regularUser.getUserName(), foundUser.getUserName(), "User names should match");
        assertEquals(regularUser.getEmail(), foundUser.getEmail(), "Emails should match");
        assertEquals(regularUser.getPassword(), foundUser.getPassword(), "Passwords should match");
    }

    @Test
    @Order(4)
    void update() {
        RegularUser updatedUser = new RegularUser.RegularUserBuilder()
                .copy(regularUser)
                .setUserName("Updated User")
                .setEmail("updatedUser@gmail.com")
                .setPassword("updatedPassword123")
                .build();
        RegularUser updated = regularUserService.update(updatedUser);
        assertNotNull(updated, "Updated user should not be null");
        assertEquals("Updated User", updated.getUserName(), "User name should be updated");
    }

    @Test
    @Order(5)
    void findByUserName() {
        List<RegularUser> users = regularUserService.findByUserName("Updated User");
        assertFalse(users.isEmpty(), "User list should not be empty");

        RegularUser user = users.get(0);
        assertEquals("Updated User", user.getUserName());

        for (RegularUser u : users) {
            assertEquals("Updated User", u.getUserName());
        }
    }
    @Test
    @Order(6)
    void findByEmail() {
        List<RegularUser> foundUser =  regularUserService.findByEmail("updatedUser@gmail.com");
        assertNotNull(foundUser, "Found user should not be null");
    }

    @Test
    @Order(7)
    void findAll() {
        Iterable<RegularUser> users = regularUserService.findAll();
        assertNotNull(users, "Users should not be null");
        assertTrue(users.iterator().hasNext(), "There should be at least one user");
        assertTrue(users.spliterator().getExactSizeIfKnown() > 0, "There should be more than zero users");
    }

    @Test
    @Order(8)
    void delete() {
        regularUserService.delete(regularUser.getUserID());
        RegularUser deletedUser = regularUserService.read(regularUser.getUserID());
        assertNull(deletedUser, "Deleted user should be null");
    }


}