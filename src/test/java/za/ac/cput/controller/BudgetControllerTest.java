package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.ac.cput.domain.Budget;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.factory.BudgetFactory;
import za.ac.cput.factory.RegularUserFactory;
import za.ac.cput.repository.RegularUserRepository;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BudgetControllerTest {

    private Budget budget;
    private RegularUser regularUser;

    @Autowired
    private RegularUserRepository regularUserRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/budget";
    }



    @BeforeEach
    void setUp() {
        regularUser = RegularUserFactory.createRegularUser("Ranelani Engel", "engel@example.com", "securePassword123");
        regularUser = regularUserRepository.save(regularUser);

     budget = BudgetFactory.createBudget("January",
             "2025",
             5000.00,
             regularUser);

        String url = getBaseUrl() + "/create";
        ResponseEntity<Budget> response = restTemplate.postForEntity(url, budget, Budget.class);
        assertEquals(200, response.getStatusCodeValue(), "Setup create budget failed");
        budget = response.getBody();
        assertNotNull(budget);
        assertNotNull(budget.getBudgetId());

    }

    @Test
    @Order(1)
    void create() {
        assertNotNull(budget);
        assertNotNull(budget.getBudgetId());
    }

    @Test
    @Order(2)
    void read() {
        String url = getBaseUrl() + "/read/" + budget.getBudgetId();
        ResponseEntity<Budget> response = restTemplate.getForEntity(url, Budget.class);
        assertEquals(200, response.getStatusCodeValue(), "Read budget failed");
        Budget foundBudget = response.getBody();
        assertNotNull(foundBudget, "Found budget should not be null");
        assertEquals(budget.getBudgetId(), foundBudget.getBudgetId(), "Budget IDs should match");
    }

    @Test
    @Order(3)
    void update() {
        Budget updatedBudget = new Budget.BudgetBuilder()
                .copy(budget)
                .setMonth("February")
                .setLimitAmount(6000.00)
                .build();

        String url = getBaseUrl() + "/update";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Budget> entity = new HttpEntity<>(updatedBudget, headers);

        ResponseEntity<Budget> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                Budget.class
        );

        assertEquals(200, response.getStatusCodeValue(), "Update budget failed");

        Budget updated = response.getBody();
        assertNotNull(updated, "Updated budget should not be null");
        assertEquals("February", updated.getMonth(), "Month should be updated");

        budget = updated;
    }


    @Test
    @Order(4)
    void findByMonth() {
        String url = getBaseUrl() + "/findByMonth/" + budget.getMonth();
        ResponseEntity<Budget[]> response = restTemplate.getForEntity(url, Budget[].class);
        assertEquals(200, response.getStatusCodeValue(), "Find by month failed");

        Budget[] foundBudgets = response.getBody();
        assertNotNull(foundBudgets, "Found budgets should not be null");
        assertTrue(foundBudgets.length > 0, "Should find at least one budget for the month");

        for (Budget b : foundBudgets) {
            assertEquals(budget.getMonth(), b.getMonth(), "Month should match");
        }
    }

    @Test
    @Order(5)
    void findByLimitAmountGreaterThan() {
        String url = getBaseUrl() + "/findByLimitAmountGreaterThan/3000.00";
        ResponseEntity<Budget[]> response = restTemplate.getForEntity(url, Budget[].class);
        assertEquals(200, response.getStatusCodeValue(), "Find by limit amount failed");

        Budget[] foundBudgets = response.getBody();
        assertNotNull(foundBudgets, "Found budgets should not be null");
        assertTrue(foundBudgets.length > 0, "Should find budgets with limit greater than 3000");

        for (Budget b : foundBudgets) {
            assertTrue(b.getLimitAmount() > 3000.00, "Limit amount should be greater than 3000");
        }
    }

    @Test
    @Order(6)
    void findByYear() {
        String url = getBaseUrl() + "/findByYear/" + budget.getYear();
        ResponseEntity<Budget[]> response = restTemplate.getForEntity(url, Budget[].class);
        assertEquals(200, response.getStatusCodeValue(), "Find by year failed");

        Budget[] foundBudgets = response.getBody();
        assertNotNull(foundBudgets, "Found budgets should not be null");
        assertTrue(foundBudgets.length > 0, "Should find at least one budget for the year");

        for (Budget b : foundBudgets) {
            assertEquals(budget.getYear(), b.getYear(), "Year should match");
        }
    }

    @Test
    @Order(7)
    void findAll() {
        String url = getBaseUrl() + "/findAll";
        ResponseEntity<Budget[]> response = restTemplate.getForEntity(url, Budget[].class);
        assertEquals(200, response.getStatusCodeValue(), "Find all budgets failed");

        Budget[] allBudgets = response.getBody();
        assertNotNull(allBudgets, "List of all budgets should not be null");
        assertTrue(allBudgets.length > 0, "Should find at least one budget");

        boolean found = false;
        for (Budget b : allBudgets) {
            if (b.getBudgetId().equals(budget.getBudgetId())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Created budget should be in the list of all budgets");
    }

    @Test
    @Order(8)
    void delete() {
        String url = getBaseUrl() + "/delete/" + budget.getBudgetId();
        restTemplate.delete(url);

        String readUrl = getBaseUrl() + "/read/" + budget.getBudgetId();
        ResponseEntity<Budget> response = restTemplate.getForEntity(readUrl, Budget.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}