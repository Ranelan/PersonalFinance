package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Role; // Make sure to import your Role enum
import za.ac.cput.domain.User;

import java.util.List;
import java.util.Optional;

@Repository // It's good practice to add the @Repository annotation
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserNameOrEmail(String userName, String email);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByRole_Name(String name); // Added for querying by role name
}