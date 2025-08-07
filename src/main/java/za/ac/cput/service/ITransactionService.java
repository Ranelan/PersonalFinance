/*   ITransaction Service Class
     Author: Lebuhang Nyanyantsi (22184353)
     Date: 26 July 2025 */

package za.ac.cput.service;

import za.ac.cput.domain.Transaction;
import java.util.List;

public interface ITransactionService extends IService<Transaction, Long> {
    Transaction create(Transaction transaction);
    Transaction read(Long id);
    Transaction update(Transaction transaction);
    boolean delete(Long id);
    List<Transaction> findAll();
}