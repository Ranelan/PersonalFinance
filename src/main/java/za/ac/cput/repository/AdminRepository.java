/*
 * AdminRepository.java
 * Goal Repository Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 25 May 2025*/

package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.User;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findByAdminCode(String adminCode);
    List<Admin> findByUserName(String userName);
    List<Admin> findByEmail(String email);
    List<Admin> findByPassword(String password);



}
