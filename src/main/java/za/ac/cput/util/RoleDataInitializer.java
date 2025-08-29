package za.ac.cput.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.ac.cput.domain.Role;
import za.ac.cput.repository.RoleRepository;

@Component
public class RoleDataInitializer {
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(new Role("ADMIN"));
        }
        if (roleRepository.findByName("REGULAR_USER").isEmpty()) {
            roleRepository.save(new Role("REGULAR_USER"));
        }
    }
}

