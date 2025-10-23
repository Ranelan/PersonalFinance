/* GoalRepository.java
        Goal Controller class
     Author: Ranelani Engel(221813853
     Date: 25 May 2025 */

package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Goal;
import za.ac.cput.service.IGoalService;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/goal")
public class GoalController {

    private final IGoalService goalService;

    @Autowired
    public GoalController(IGoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/create")
    public ResponseEntity<Goal> create(@RequestBody Goal goal) {
        Goal createdGoal = goalService.create(goal);
        return createdGoal != null ? ResponseEntity.ok(createdGoal) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/read/{goalId}")
    public ResponseEntity<Goal> read(@PathVariable Long goalId) {
        Goal goal = goalService.read(goalId);
        return goal != null ? ResponseEntity.ok(goal) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Goal> update(@RequestBody Goal goal) {
        Goal updatedGoal = goalService.update(goal);
        return updatedGoal != null ? ResponseEntity.ok(updatedGoal) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{goalId}")
    public ResponseEntity<Void> delete(@PathVariable Long goalId) {
        try {
            goalService.delete(goalId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByGoalName/{goalName}")
    public ResponseEntity<List<Goal>> findByGoalName(@PathVariable String goalName) {
        List<Goal> goals = goalService.findByGoalName(goalName);
        return goals != null && !goals.isEmpty() ? ResponseEntity.ok(goals) : ResponseEntity.notFound().build();
    }

    @GetMapping("/findByDeadLine/{deadLine}")
    public ResponseEntity<List<Goal>> findByDeadLine(@PathVariable String deadLine) {
        try {
            LocalDate parsedDate = LocalDate.parse(deadLine); // Converts "2025-12-31" to LocalDate
            List<Goal> goals = goalService.findByDeadLine(String.valueOf(parsedDate));
            if (goals != null && !goals.isEmpty()) {
                return ResponseEntity.ok(goals);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Goal>> findAll() {
        List<Goal> goals = goalService.findAll();
        return goals != null && !goals.isEmpty() ? ResponseEntity.ok(goals) : ResponseEntity.noContent().build();
    }

    @GetMapping("/findByRegularUser_MembershipID/{membershipId}")
    public ResponseEntity<List<Goal>> findByRegularUser_MembershipID(@PathVariable String membershipId) {
        List<Goal> goals = goalService.findByRegularUser_MembershipID(membershipId);
        return goals != null && !goals.isEmpty() ? ResponseEntity.ok(goals) : ResponseEntity.notFound().build();
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Goal>> getByUser(@PathVariable Long userId) {
        List<Goal> goals = goalService.findByUserId(userId);
        if (goals != null && !goals.isEmpty()) {
            return ResponseEntity.ok(goals);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
