package za.ac.cput.service;

import za.ac.cput.domain.RegularUser;

import java.util.List;

public interface IRegularUserService extends IService<RegularUser, Long>{
    RegularUser read(Long id);
    List<RegularUser> findByUserName(String userName);
    List<RegularUser> findByEmail(String email);
    List<RegularUser> findAll();
    RegularUser logIn(String usernameOrEmail, String password);
}
