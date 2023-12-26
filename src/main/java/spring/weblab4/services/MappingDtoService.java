package spring.weblab4.services;

import org.springframework.stereotype.Service;
import spring.weblab4.dto.LogDto;
import spring.weblab4.models.Log;
import spring.weblab4.models.User;
import spring.weblab4.repositories.UserRepository;

@Service
public class MappingDtoService {
    //private final UserRepository userRepository;

/*    public MappingDtoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    public LogDto mapToLogDto(Log logEntity){
        LogDto dto = new LogDto();
        User tmp = logEntity.getLogUser();
        dto.setUsername(tmp.getUsername());
        dto.setUserId(tmp.getId());
        dto.setAction(logEntity.getLogAction().getAction()); // hoho
        dto.setTime(logEntity.getActionTime());
        return dto;
    }

/*    public Log mapToLogEntity(LogDto dto){
        Log logEntity = new Log();
        if (userRepository.findById(dto.getUserId()).isPresent())
            logEntity.setUser(userRepository.findById(dto.getUserId()).get());
        logEntity.setAction_time(dto.getTime());
        logEntity.setAction();
    }*/
}
