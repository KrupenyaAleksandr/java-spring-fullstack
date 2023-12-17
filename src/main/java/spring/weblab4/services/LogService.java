package spring.weblab4.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import spring.weblab4.models.Log;
import spring.weblab4.models.LogAction;
import spring.weblab4.models.User;
import spring.weblab4.util.LogEvent;

@Service
public class LogService {
    private final ApplicationEventPublisher applicationEventPublisher;

    public LogService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishLogEvent(User user, LogAction logAction){
        LogEvent logEvent = new LogEvent(this, new Log(user, logAction));
        applicationEventPublisher.publishEvent(logEvent);
    }
}
