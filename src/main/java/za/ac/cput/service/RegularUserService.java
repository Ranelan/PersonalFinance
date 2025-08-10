package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.repository.RegularUserRepository;

import java.util.List;

@Service
public class RegularUserService implements IRegularUserService{

    private final RegularUserRepository regularUserRepository;

    @Autowired
    public RegularUserService(RegularUserRepository regularUserRepository) {
        this.regularUserRepository = regularUserRepository;
    }

    @Override
    public RegularUser create(RegularUser regularUser){
        return regularUserRepository.save(regularUser);
    }

    @Override
    public RegularUser read(Long id) {
        return regularUserRepository.findById(id).orElse(null);
    }

    @Override
    public RegularUser update(RegularUser regularUser) {
        if (regularUser.getUserID() != null
                && regularUserRepository.existsById(regularUser.getUserID())
        ) {
            return regularUserRepository.save(regularUser);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        regularUserRepository.deleteById(id);
    }

    @Override
    public List<RegularUser> findByUserName(String userName) {
        return regularUserRepository.findByUserName(userName);
    }

    @Override
    public List<RegularUser> findByEmail(String email) {
        return regularUserRepository.findByEmail(email);
    }

    @Override
    public List<RegularUser> findAll() {
        return regularUserRepository.findAll();
    }

    @Override
    public RegularUser logIn(String usernameOrEmail, String password) {
        List<RegularUser> foundRegularUser = regularUserRepository.findByUserName(usernameOrEmail);
        if (foundRegularUser.isEmpty()) {
            // Try to find by email if not found by username
            foundRegularUser = regularUserRepository.findByEmail(usernameOrEmail);
        }

        if (!foundRegularUser.isEmpty()) {
            RegularUser regularUser = foundRegularUser.get(0);
            if (regularUser.getPassword().equals(password)) {
                return regularUser; // Login successful
            }
        }

        return null;
    }
    }
