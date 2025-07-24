/* GoalRepository.java
     IGoalService Interface
     Author: Ranelani Engel(221813853
     Date: 25 May 2025 */

package za.ac.cput.service;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Goal;

import java.util.List;

public interface IGoalService extends IService<Goal, Long> {

    List<Goal> findByGoalId(Long goalId);
    List<Goal> findByGoalName(String goalName);
    List<Goal> findByDeadLine(String deadLine);
    List<Goal> findAll(Goal goal);
    List<Goal> findByRegularUser_MembershipID(String membershipId);
}
