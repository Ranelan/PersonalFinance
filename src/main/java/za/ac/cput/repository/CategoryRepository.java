/* CategoryRepository.java
   Category Repository Interface
   Author: Lebuhang Nyanyantsi (222184353)
   Date: 25 May 2025 */

package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByName(String name);

    List<Category> findByType(String type);

    List<Category> findAll();

    List<Category> findByRegularUser_UserID(Long userId);
}
