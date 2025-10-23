package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Goal;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.repository.GoalRepository;
import za.ac.cput.repository.RegularUserRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class GoalService implements IGoalService {

    private final GoalRepository goalRepository;
    private final RegularUserRepository regularUserRepository; // Inject RegularUser repo

    @Autowired
    public GoalService(GoalRepository goalRepository, RegularUserRepository regularUserRepository) {
        this.goalRepository = goalRepository;
        this.regularUserRepository = regularUserRepository;
    }

    @Override
    public Goal create(Goal goal) {
        return goalRepository.save(goal);
    }

    @Override
    public Goal read(Long aLong) {
        return goalRepository.findById(aLong).orElse(null);
    }

    @Override
    public Goal update(Goal goal) {
        if (goal.getGoalId() != null && goalRepository.existsById(goal.getGoalId())) {
            Goal existing = goalRepository.findById(goal.getGoalId()).orElse(null);
            if (existing != null) {
                // Ensure RegularUser is managed before saving
                RegularUser managedUser = null;
                if (goal.getRegularUser() != null && goal.getRegularUser().getUserID() != null) {
                    managedUser = regularUserRepository.findById(goal.getRegularUser().getUserID()).orElse(null);
                }

                Goal updated = new Goal.GoalBuilder()
                        .copy(existing)
                        .setRegularUser(managedUser)
                        .setGoalName(goal.getGoalName())
                        .setTargetAmount(goal.getTargetAmount())
                        .setCurrentAmount(goal.getCurrentAmount())
                        .setDeadline(goal.getDeadLine())
                        .build();

                return goalRepository.save(updated);
            }
        }
        return null;
    }

    @Override
    public void delete(Long aLong) {
        try {
            goalRepository.deleteById(aLong);
        } catch (Exception e) {
            System.out.println("Error deleting goal: " + e.getMessage());
        }
    }

    @Override
    public List<Goal> findByGoalId(Long goalId) {
        return Collections.singletonList(goalRepository.findById(goalId).orElse(null));
    }

    @Override
    public List<Goal> findByGoalName(String goalName) {
        return goalRepository.findByGoalName(goalName);
    }

    @Override
    public List<Goal> findByDeadLine(String deadLine) {
        return goalRepository.findByDeadLine(LocalDate.parse(deadLine));
    }

    @Override
    public List<Goal> findAll() {
        return goalRepository.findAll();
    }

    @Override
    public List<Goal> findByRegularUser_MembershipID(String membershipId) {
        return goalRepository.findByRegularUser_MembershipID(membershipId);
    }

    @Override
    public List<Goal> findByUserId(Long userId) {
        return goalRepository.findByRegularUser_UserID(userId);
    }
}
