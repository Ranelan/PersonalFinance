/*
 * RegularUserRepository.java
 * Goal Repository Class
 * Author: Scelo Kevin Nyandeni(23018695)
 * Date: 25 May 2025*/

package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.RegularUser;

import java.util.List;

@Repository
public interface RegularUserRepository extends JpaRepository<RegularUser, Long> {

    List<RegularUser> findByMembershipID(String membershipID);
    List<RegularUser> findByUserName(String userName);
    List<RegularUser> findByEmail(String email);
    List<RegularUser> findByPassword(String password);


}
