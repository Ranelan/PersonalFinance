package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Admin;
import za.ac.cput.repository.AdminRepository;
import java.util.List;


@Service
public class AdminService implements IAdminService{

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin read(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        if (admin.getUserID() != null && adminRepository.existsById(admin.getUserID())) {
            return adminRepository.save(admin);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public List<Admin> findByUserName(String userName) {
        return adminRepository.findByUserName(userName);
    }

    @Override
    public List<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }
}
