package spring.weblab4.util;

import org.springframework.context.ApplicationEvent;
import spring.weblab4.models.Log;

public class LogEvent extends ApplicationEvent{

    private final Log log;

    public LogEvent(Object source, Log log) {
        super(source);
        this.log = log;
    }

    public Log getLog(){
        return this.log;
    }
}
