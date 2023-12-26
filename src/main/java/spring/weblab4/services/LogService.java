package spring.weblab4.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.weblab4.dto.LogDto;
import spring.weblab4.models.Log;
import spring.weblab4.models.LogAction;
import spring.weblab4.models.User;
import spring.weblab4.repositories.LogActionRepository;
import spring.weblab4.repositories.LogRepository;
import spring.weblab4.util.LogEvent;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MappingDtoService mappingDtoService;
    private final LogRepository logRepository;
    private final LogActionRepository logActionRepository;

    public LogService(ApplicationEventPublisher applicationEventPublisher, MappingDtoService mappingDtoService, LogRepository logRepository, LogActionRepository logActionRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.mappingDtoService = mappingDtoService;
        this.logRepository = logRepository;
        this.logActionRepository = logActionRepository;
    }

    public void publishLogEvent(User user, LogAction logAction){
        LogEvent logEvent = new LogEvent(this, new Log(user, logAction));
        applicationEventPublisher.publishEvent(logEvent);
    }

    public Page<LogDto> findPaginated(Pageable pageable){
        int currentPage = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int startItem = currentPage * pageSize;

/*        if (logRepository.count() < startItem){
            logs = Collections.emptyList();
        }
        else {
            int index = (int) Math.min(startItem + pageSize, logRepository.count());
            logs =
        }*/
        Page<Log> logs = logRepository.findAllBy(PageRequest.of(currentPage, pageSize));
        Page<LogDto> logsPage = logs.map(this::mapToLogDto);
        return logsPage;
        //pagesCount = logsPage.getTotalPages();
/*        return logs.stream()
                .map(this::mapToLogDto)
                .collect(Collectors.toList());*/
    }

    public Map<String, Integer> getAllByAction(Calendar fromDate, Calendar toDate){
        List<LogAction> logActions = logActionRepository.findAll();
        Map<String, Integer> logsStats = new LinkedHashMap<String, Integer>();
        for (LogAction logAction : logActions){
            if (fromDate != null && toDate != null) {
                logsStats.put(logAction.getAction(), logRepository.findAllBylogActionAndActionTimeBetween(logAction, fromDate, toDate).size());
            }
            else {
                logsStats.put(logAction.getAction(), logRepository.findAllBylogAction(logAction).size());
            }
        }
        return logsStats;
    }

    private LogDto mapToLogDto(Log logEntity){
        LogDto dto = new LogDto();
        User tmp = logEntity.getLogUser();
        dto.setUsername(tmp.getUsername());
        dto.setUserId(tmp.getId());
        dto.setAction(logEntity.getLogAction().getAction());
        dto.setTime(logEntity.getActionTime());
        return dto;
    }
}
