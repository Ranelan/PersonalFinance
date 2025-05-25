package za.ac.cput.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Budget;
import za.ac.cput.domain.Goal;
import za.ac.cput.factory.GoalFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.class)
class GoalRepositoryTest {

    @Autowired
    private GoalRepository goalRepository;
    private Goal goal = new GoalFactory().createGoal("Save for registration", 10000.00, 5000.00,  LocalDate.of(2026, 01, 10));

     @Test
     void a_save(){
         goal = goalRepository.save(goal);
         assertNotNull(goal);
         System.out.println("Saved: " + goal);
     }

     @Test
    void b_read(){
         Goal saved = goalRepository.save(goal);
         Optional<Goal> found = goalRepository.findById(saved.getGoalId());
         assertTrue(found.isPresent());
         System.out.println("Read: " + found.get());
     }

     @Test
    void c_update(){

             Goal saved = goalRepository.save(goal);
             Goal updated = new Goal.GoalBuilder()
                     .copy(saved)
                        .setTargetAmount(12000.00)
                        .setCurrentAmount(6000.00)
                        .build();
                goalRepository.save(updated);
                Optional<Goal> found = goalRepository.findById(saved.getGoalId());
                assertTrue(found.isPresent());
                assertEquals(12000.00, found.get().getTargetAmount());

         }

//    @Test
//    void g_delete() {
//        Goal saved = goalRepository.save(goal);
//        goalRepository.deleteById(saved.getGoalId());
//        Optional<Goal> found = goalRepository.findById(saved.getGoalId());
//        assertFalse(found.isPresent());
//        System.out.println("Deleted: " + saved);
//    }

    @Test
    void d_findAll() {
        goalRepository.save(goal);
        assertFalse(goalRepository.findAll().isEmpty());
        System.out.println("All Goals: " + goalRepository.findAll());
    }


    @Test
    void e_findByGoalName() {
        Goal saved = goalRepository.save(goal);
        List<Goal> found = goalRepository.findByGoalName(saved.getGoalName());
        assertFalse(found.isEmpty());
        assertEquals(saved.getGoalName(), found.get(0).getGoalName());
        System.out.println("Found by Goal Name: " + found);

    }

    @Test
    void f_findByDeadline() {
        Goal saved = goalRepository.save(goal);
        List<Goal> found = goalRepository.findByDeadLine(saved.getDeadLine());
        assertFalse(found.isEmpty());
        assertEquals(saved.getDeadLine(), found.get(0).getDeadLine());
        System.out.println("Found by Deadline: " + found);
    }
}