package spring.weblab4.restcontrollers;

import org.springframework.web.bind.annotation.*;
import spring.weblab4.dto.NoteDto;
import spring.weblab4.repositories.UserRepository;
import spring.weblab4.services.MappingDtoService;
import spring.weblab4.services.NoteService;

import java.io.IOException;
import java.security.Principal;

@RequestMapping("/rest")
@RestController
public class RestNoteController {
    private final NoteService noteService;
    private final UserRepository userRepository;
    private final MappingDtoService mappingDtoService;

    public RestNoteController(NoteService noteService, UserRepository userRepository, MappingDtoService mappingDtoService) {
        this.noteService = noteService;
        this.userRepository = userRepository;
        this.mappingDtoService = mappingDtoService;
    }

    @GetMapping("/notes/get-all-notes")
    public String getAllNotes(Principal principal) throws IOException {
        String notesJSON = noteService.getAllNotes(userRepository.findByUsername(principal.getName()).get().getId());
        return notesJSON;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/notes/save-note", consumes="application/json")
    public void saveNote(@RequestBody NoteDto note,
                         Principal principal) {
        noteService.saveNote(userRepository.findByUsername(principal.getName()).get().getId(), note);
    }
}
