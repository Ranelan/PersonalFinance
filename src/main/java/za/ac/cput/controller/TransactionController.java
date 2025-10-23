package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Transaction;
import za.ac.cput.service.TransactionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }


    @PostMapping("/create")
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction){
        Transaction createdTransaction = transactionService.create(transaction);
        return ResponseEntity.ok(createdTransaction);
    }

    // Read (get) a transaction by id
    @GetMapping("/read/{id}")
    public ResponseEntity<Optional<Transaction>> read(@PathVariable String id){
        Optional<Transaction> transaction = Optional.ofNullable(transactionService.read(Long.valueOf(id)));
        if(transaction.isPresent()){
            return ResponseEntity.ok(transaction);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/update")
    public ResponseEntity<Transaction> update(@RequestBody Transaction transaction){
        Transaction updatedTransaction = transactionService.update(transaction);
        if(updatedTransaction != null){
            return ResponseEntity.ok(updatedTransaction);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        transactionService.delete(Long.valueOf(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAll(){
        List<Transaction> transactions = transactionService.findAll();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Transaction>> getByUser(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.findByUserId(userId);
        if (transactions != null && !transactions.isEmpty()) {
            return ResponseEntity.ok(transactions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
