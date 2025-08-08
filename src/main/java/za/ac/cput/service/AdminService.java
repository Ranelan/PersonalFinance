package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Permission;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.repository.AdminRepository;
import za.ac.cput.repository.RegularUserRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final RegularUserRepository regularUserRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, RegularUserRepository regularUserRepository) {
        this.adminRepository = adminRepository;
        this.regularUserRepository = regularUserRepository;
    }

    @Override
    public Admin create(Admin admin) {
        // Grant all permissions to every admin by default
        List<Permission> allPermissions = Arrays.asList(Permission.values());

        Admin newAdmin = new Admin.AdminBuilder()
                .setUserName(admin.getUserName())
                .setEmail(admin.getEmail())
                .setPassword(admin.getPassword())
                .setAdminCode(admin.getAdminCode())
                .setPermissions(allPermissions)
                .build();

        return adminRepository.save(newAdmin);
    }

    @Override
    public Admin read(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        return adminRepository.findById(admin.getUserID())
                .map(existingAdmin -> {
                    // Keep existing permissions intact
                    Admin updatedAdmin = new Admin.AdminBuilder()
                            .copy(existingAdmin)
                            .setUserName(admin.getUserName())
                            .setEmail(admin.getEmail())
                            .setPassword(admin.getPassword())
                            .setAdminCode(admin.getAdminCode())
                            .build();

                    return adminRepository.save(updatedAdmin);
                })
                .orElse(null);
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

    @Override
    public List<RegularUser> viewAllRegularUsers() {
        return regularUserRepository.findAll();
    }

    @Override
    public void deleteRegularUserById(Long id) {
        regularUserRepository.deleteById(id);
    }

    @Override
    public void updateRegularUserById(Long id, RegularUser updatedUser) {
        regularUserRepository.findById(id)
                .map(existingUser -> {
                    // Copy existing user and update fields
                    RegularUser newUser = new RegularUser.RegularUserBuilder()
                            .copy(existingUser)
                            .setUserName(updatedUser.getUserName())
                            .setEmail(updatedUser.getEmail())
                            .setPassword(updatedUser.getPassword())
                            .build();
                    return regularUserRepository.save(newUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Admin logIn(String usernameOrEmail, String password) {
        List<Admin> foundAdmins = adminRepository.findByUserName(usernameOrEmail);
        if (foundAdmins.isEmpty()) {
            // Try to find by email if not found by username
            foundAdmins = adminRepository.findByEmail(usernameOrEmail);
        }

        if (!foundAdmins.isEmpty()) {
            Admin admin = foundAdmins.get(0);
            if (admin.getPassword().equals(password)) {
                return admin; // Login successful
            }
        }

        return null;
    }
}
