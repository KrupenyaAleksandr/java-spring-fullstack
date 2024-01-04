package spring.weblab4.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Service;
import spring.weblab4.dto.NoteDto;
import spring.weblab4.models.Note;
import spring.weblab4.models.User;
import spring.weblab4.repositories.NoteRepository;
import spring.weblab4.repositories.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public String getAllNotes(int userId, String[] tags) throws IOException {
        User user = userRepository.findById(userId).get();
        List<Note> notes = new ArrayList<Note>();
        if (tags != null){
            for (String tag : tags){
                List<Note> newNotesWithTags = noteRepository.findAllByUserIdAndTag(user, tag);
                notes = Stream.concat(notes.stream(), newNotesWithTags.stream()).collect(Collectors.toList());
            }
        }
        else {
            notes = noteRepository.findAllByUserId(user);
        }
        List<NoteDto> notesDto = notes.stream().map(mappingDtoService::mapToNoteDto).toList();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        objectMapper.writeValue(out, notesDto);
        final byte[] data = out.toByteArray();
        return new String(data);
    }

    public String getAllTags(int userId) throws IOException {
        User user = userRepository.findById(userId).get();
        List<String> tags = noteRepository.findUniqueTagsByUserId(user);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        objectMapper.writeValue(out, tags);
        final byte[] data = out.toByteArray();
        return new String(data);
    }

    public void saveNote(int userId, NoteDto noteToSave){
        System.out.println(noteToSave.getNoteId());
        Note note = mappingDtoService.mapToNote(noteToSave);
        note.setUserId(userRepository.findById(userId).get());
        noteRepository.save(note);
    }

    public void deleteNote(Integer noteId){
        noteRepository.deleteById(noteId);
    }
}
