package spring.weblab4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.weblab4.models.User;
import spring.weblab4.repositories.UserRepository;

import java.util.Optional;

@Service
public class ValidateUserService {
    private final UserRepository userRepository;

    @Autowired
    public ValidateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserAlreadyExists(String s){
        Optional<User> user = userRepository.findByUsername(s);
        System.out.println(user.isEmpty());
        return user.isEmpty();
    }
}
