package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByType(String type);

    List<Transaction> findByAmountGreaterThan(double amount);

    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
