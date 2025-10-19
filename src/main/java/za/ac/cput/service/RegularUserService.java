package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.repository.RegularUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RegularUserService implements IRegularUserService{

    private final RegularUserRepository regularUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegularUserService(RegularUserRepository regularUserRepository, PasswordEncoder passwordEncoder) {
        this.regularUserRepository = regularUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegularUser create(RegularUser regularUser){
        // Hash the password before saving
        regularUser.setPassword(passwordEncoder.encode(regularUser.getPassword()));
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
            // Hash the password before saving (if not already hashed)
            regularUser.setPassword(passwordEncoder.encode(regularUser.getPassword()));
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
    public Optional<RegularUser> findByEmail(String email) {
        return regularUserRepository.findByEmail(email);
    }

    @Override
    public List<RegularUser> findAll() {
        return regularUserRepository.findAll();
    }

    @Override
    public RegularUser logIn(String email, String password) {
        Optional<RegularUser> foundRegularUser = regularUserRepository.findByEmail(email);
        if (foundRegularUser.isPresent()) {
            RegularUser regularUser = foundRegularUser.get();
            if (passwordEncoder.matches(password, regularUser.getPassword())) {
                return regularUser; // Login successful
            }
        }
        return null;
    }
    }
