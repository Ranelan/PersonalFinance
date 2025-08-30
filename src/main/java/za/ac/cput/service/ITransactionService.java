/*   ITransaction Service Class
     Author: Lebuhang Nyanyantsi (22184353)
     Date: 26 July 2025 */

package za.ac.cput.service;

import za.ac.cput.domain.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface ITransactionService extends IService<Transaction, Long> {

    List<Transaction> findAll();
    List<Transaction> findByType(String type);
    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);


}