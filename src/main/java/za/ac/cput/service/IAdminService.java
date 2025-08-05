package za.ac.cput.service;

import za.ac.cput.domain.Admin;
import java.util.List;


public interface IAdminService extends IService<Admin, Long> {
    Admin read(Long id);
    List<Admin> findByUserName(String userName);
    List<Admin> findByEmail(String email);
    List<Admin> findAll();
}
