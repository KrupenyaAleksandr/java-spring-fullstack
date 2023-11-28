package spring.weblab4.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.weblab4.models.User;
import spring.weblab4.repositories.UserRepository;

import javax.management.Query;

@Service
public class RegistrationService {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void register(User user){
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }
}
