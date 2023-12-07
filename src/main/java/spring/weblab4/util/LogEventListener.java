package spring.weblab4.util;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import spring.weblab4.models.Log;
import spring.weblab4.models.LogAction;
import spring.weblab4.models.User;
import spring.weblab4.repositories.LogRepository;
import spring.weblab4.repositories.UserRepository;

@Component
public class LogEventListener{

    private final LogRepository logRepository;
    private final UserRepository userRepository;

    public LogEventListener(LogRepository logRepository, UserRepository userRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    @EventListener
    public void onApplicationEvent(LogEvent logEvent){
        logRepository.save(logEvent.getLog());
    }

    @EventListener(AuthenticationSuccessEvent.class)
    public void onSuccessLogin(AuthenticationSuccessEvent event){
        //logRepository.save(new Log(userRepository.findByUsername(event.getAuthentication().getName()).get(),
        //        new LogAction(11)));
    }

}
