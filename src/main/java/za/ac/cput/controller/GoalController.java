/* GoalRepository.java
        Goal Controller class
     Author: Ranelani Engel(221813853
     Date: 25 May 2025 */

package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Goal;
import za.ac.cput.service.IGoalService;

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
    public ResponseEntity<Goal> create(@PathVariable Goal goal){
        Goal createdGoal = goalService.create(goal);
        if (createdGoal != null) {
            return ResponseEntity.ok(createdGoal);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("read/{goalId}")
    public ResponseEntity<Goal> read(@PathVariable Long goalId) {
        Goal goal = goalService.read(goalId);
        if (goal != null) {
            return ResponseEntity.ok(goal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Goal> update(@RequestBody Goal goal) {
        Goal updatedGoal = goalService.update(goal);
        if (updatedGoal != null) {
            return ResponseEntity.ok(updatedGoal);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping("/findByGoalId/{goalId}")
    public ResponseEntity<List<Goal>> findByGoalId(@PathVariable Long goalId) {
        List<Goal> goals = goalService.findByGoalId(goalId);
        if (goals != null && !goals.isEmpty()) {
            return ResponseEntity.ok(goals);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByGoalName/{goalName}")
    public ResponseEntity<List<Goal>> findByGoalName(@PathVariable String goalName) {
        List<Goal> goals = goalService.findByGoalName(goalName);
        if (goals != null && !goals.isEmpty()) {
            return ResponseEntity.ok(goals);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByDeadLine/{deadLine}")
    public ResponseEntity<List<Goal>> findByDeadLine(@PathVariable String deadLine) {
        List<Goal> goals = goalService.findByDeadLine(deadLine);
        if (goals != null && !goals.isEmpty()) {
            return ResponseEntity.ok(goals);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Goal>> findAll(@PathVariable Goal goal) {
        List<Goal> goals = goalService.findAll(goal);
        if (goals != null && !goals.isEmpty()) {
            return ResponseEntity.ok(goals);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
