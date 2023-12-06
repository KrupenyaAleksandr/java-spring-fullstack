package spring.weblab4.util;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import spring.weblab4.models.Log;
import spring.weblab4.models.LogAction;
import spring.weblab4.models.User;

@Component
public class EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishLogEvent(User user, LogAction logAction){
        LogEvent logEvent = new LogEvent(this, new Log(user, logAction));
        applicationEventPublisher.publishEvent(logEvent);
    }
}
