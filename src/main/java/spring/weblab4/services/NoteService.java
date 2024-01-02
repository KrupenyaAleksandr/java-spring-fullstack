package spring.weblab4.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import spring.weblab4.dto.NoteDto;
import spring.weblab4.models.Note;
import spring.weblab4.repositories.NoteRepository;
import spring.weblab4.repositories.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final MappingDtoService mappingDtoService;
    private final ObjectMapper objectMapper;


    public NoteService(NoteRepository noteRepository, UserRepository userRepository, MappingDtoService mappingDtoService, ObjectMapper objectMapper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.mappingDtoService = mappingDtoService;
        this.objectMapper = objectMapper;
    }

    public String getAllNotes(int userId) throws IOException {
        List<Note> notes = noteRepository.findAllByuserId(userRepository.findById(userId).get());
        List<NoteDto> notesDto = notes.stream().map(mappingDtoService::mapToNoteDto).toList();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        objectMapper.writeValue(out, notesDto);
        final byte[] data = out.toByteArray();
        return new String(data);
    }

    public void saveNote(int userId, NoteDto noteToSave){
        Note note = mappingDtoService.mapToNote(noteToSave);
        note.setUserId(userRepository.findById(userId).get());
        noteRepository.save(note);
    }
}
