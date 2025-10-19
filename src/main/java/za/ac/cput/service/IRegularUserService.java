package za.ac.cput.service;

import za.ac.cput.domain.RegularUser;

import java.util.List;
import java.util.Optional;

public interface IRegularUserService extends IService<RegularUser, Long>{
    RegularUser read(Long id);
    List<RegularUser> findByUserName(String userName);
    Optional<RegularUser> findByEmail(String email);
    List<RegularUser> findAll();
    RegularUser logIn(String usernameOrEmail, String password);
}
