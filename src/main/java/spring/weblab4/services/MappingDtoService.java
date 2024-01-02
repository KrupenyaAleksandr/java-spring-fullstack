package spring.weblab4.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.weblab4.dto.LogDto;
import spring.weblab4.dto.NoteDto;
import spring.weblab4.models.Log;
import spring.weblab4.models.Note;
import spring.weblab4.models.User;

import java.util.List;

@Service
public class MappingDtoService {

    private final ModelMapper modelMapper;

    public MappingDtoService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LogDto mapToLogDto(Log logEntity){
        LogDto dto = new LogDto();
        User tmp = logEntity.getLogUser();
        dto.setUsername(tmp.getUsername());
        dto.setUserId(tmp.getId());
        dto.setAction(logEntity.getLogAction().getAction());
        dto.setTime(logEntity.getActionTime());
        return dto;
    }

    public NoteDto mapToNoteDto(Note noteEntity){
        NoteDto dto = new NoteDto();
        dto.setNoteId(noteEntity.getId());
        dto.setUserId(noteEntity.getUserId().getId());
        dto.setTitle(noteEntity.getTitle());
        dto.setText(noteEntity.getText());
        dto.setTag(noteEntity.getTag());
        dto.setUpdatedTime(noteEntity.getUpdatedTime());
        return dto;
    }

    public Note mapToNote(NoteDto noteDto){
        Note note = new Note();
        if (noteDto.getNoteId() != 0)
            note.setId(noteDto.getNoteId());
        note.setTitle(noteDto.getTitle());
        note.setText(noteDto.getText());
        note.setTag(noteDto.getTag());
        return note;
    }
/*    public List<NoteDto> mapToNoteDto(List<Note> noteList){
        //return noteList.stream().map()
    }*/
/*    public Log mapToLogEntity(LogDto dto){
        Log logEntity = new Log();
        if (userRepository.findById(dto.getUserId()).isPresent())
            logEntity.setUser(userRepository.findById(dto.getUserId()).get());
        logEntity.setAction_time(dto.getTime());
        logEntity.setAction();
    }*/
}
