package spring.weblab4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.weblab4.models.User;
import spring.weblab4.repositories.PasswordTokenRepository;
import spring.weblab4.repositories.UserRepository;

import java.util.Optional;

@Service
public class ValidateService {
/*    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;

    @Autowired
    public ValidateService(UserRepository userRepository, PasswordTokenRepository passwordTokenRepository) {
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
    }

    public boolean isUserAlreadyExists(String s){
        Optional<User> user = userRepository.findByUsername(s);
        return user.isEmpty();
    }*/
}
