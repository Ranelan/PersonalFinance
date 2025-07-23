/* GoalRepository.java
     Goal Repository Interface
     Author: Ranelani Engel(221813853
     Date: 25 May 2025 */

package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Goal;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal> findByGoalName(String goalName);
    List<Goal> findByDeadLine(LocalDate deadline);


}
