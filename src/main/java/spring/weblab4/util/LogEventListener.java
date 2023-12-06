package spring.weblab4.util;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import spring.weblab4.repositories.LogRepository;

@Component
public class LogEventListener{

    private final LogRepository logRepository;

    public LogEventListener(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @EventListener
    public void onApplicationEvent(LogEvent logEvent){
        logRepository.save(logEvent.getLog());
    }

    @EventListener(AuthenticationSuccessEvent.class)
    public void onSuccessLogin(){
        //TODO
        //System.out.println(1);
    }

}
