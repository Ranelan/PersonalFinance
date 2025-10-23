package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.domain.Category;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
    List<RecurringTransaction> findByRecurrenceType(String recurrenceType);

    List<RecurringTransaction> findByNextExecution(LocalDate nextExecution);

    List<RecurringTransaction> findByCategory(Category category);
    List<RecurringTransaction> findByRegularUser(za.ac.cput.domain.RegularUser regularUser);
    List<RecurringTransaction> findByRegularUser_UserID(Long userId);

}
