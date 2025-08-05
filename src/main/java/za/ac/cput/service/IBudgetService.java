/* GoalRepository.java
     IBudgeService Interface
     Author: Ranelani Engel(221813853
     Date: 25 May 2025 */

package za.ac.cput.service;

import za.ac.cput.domain.Budget;

import java.util.List;

public interface IBudgetService extends IService<Budget, Long>{

    List<Budget> findByMonth(String month);
    List<Budget> findByLimitAmountGreaterThan(double amount);
    List<Budget> findByYear(String year);
    List<Budget> findAll();
}
