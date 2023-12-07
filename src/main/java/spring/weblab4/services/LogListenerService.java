package spring.weblab4.services;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Service;
import spring.weblab4.models.Log;
import spring.weblab4.models.LogAction;
import spring.weblab4.repositories.LogRepository;
import spring.weblab4.repositories.UserRepository;
import spring.weblab4.util.LogEvent;

@Service
public class LogListenerService {
    private final LogRepository logRepository;
    private final UserRepository userRepository;

    public LogListenerService(LogRepository logRepository, UserRepository userRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    @EventListener
    public void onApplicationEvent(LogEvent logEvent){
        logRepository.save(logEvent.getLog());
    }

    @EventListener(AuthenticationSuccessEvent.class)
    public void onSuccessLogin(AuthenticationSuccessEvent event){
        logRepository.save(new Log(userRepository.findByUsername(event.getAuthentication().getName()).get(),
                new LogAction(11)));
    }

    @EventListener(LogoutSuccessEvent.class)
    public void onSuccessLogout(LogoutSuccessEvent event){
        logRepository.save(new Log(userRepository.findByUsername(event.getAuthentication().getName()).get(),
                new LogAction(12)));
    }
}
