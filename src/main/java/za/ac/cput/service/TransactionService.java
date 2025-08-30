/*   Transaction Service Class
     Author: Lebuhang Nyanyantsi (22184353)
     Date: 26 July 2025 */


package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Transaction;
import za.ac.cput.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction create(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction read(Long aLong) {
        return transactionRepository.findById(aLong).orElse(null);
    }

    @Override
    public Transaction update(Transaction transaction) {
        if (transaction.getTransactionId() != null && transactionRepository.existsById(transaction.getTransactionId())) {
            Transaction existing = transactionRepository.findById(transaction.getTransactionId()).orElse(null);
            if (existing != null) {
                Transaction updated = new Transaction.TransactionBuilder()
                        .copy(existing)
                        .setAmount(transaction.getAmount())
                        .setDate(transaction.getDate())
                        .setDescription(transaction.getDescription())
                        .setType(transaction.getType())
                        .setRegularUser(transaction.getRegularUser())
                        .setCategory(transaction.getCategory())
                        .build();
                return transactionRepository.save(updated);
            }
        }
        return null;
    }

    @Override
    public List<Transaction> findAll() {
                return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findByType(String type) {
        return transactionRepository.findByType(type);
    }

    @Override
    public List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public void delete(Long aLong) {
        transactionRepository.deleteById(aLong);
    }

}