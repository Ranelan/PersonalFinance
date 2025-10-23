/* BudgetRepository.java
     Budget Repository Interface
     Author: Ranelani Engel(221813853
     Date: 25 May 2025 */

package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Budget;

import java.util.List;


@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {


    List<Budget> findByMonth(String month);
    List<Budget> findByLimitAmountGreaterThan(double amount);
    List<Budget> findByYear(String year);
    List<Budget> findByRegularUser_UserID(Long userId);

}
