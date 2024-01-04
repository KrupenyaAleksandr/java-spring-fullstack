package spring.weblab4.restcontrollers;

import org.springframework.web.bind.annotation.*;
import spring.weblab4.dto.NoteDto;
import spring.weblab4.repositories.UserRepository;
import spring.weblab4.services.MappingDtoService;
import spring.weblab4.services.NoteService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RequestMapping("/rest")
@RestController
public class RestNoteController {
    private final NoteService noteService;
    private final UserRepository userRepository;

    public RestNoteController(NoteService noteService, UserRepository userRepository, MappingDtoService mappingDtoService) {
        this.noteService = noteService;
        this.userRepository = userRepository;
    }

    @GetMapping("/notes/get-all-notes")
    public String getAllNotesAPI(@RequestParam(value = "selectedTags", required = false) String[] tags,
                                  Principal principal) throws IOException {
        String notesJSON = noteService.getAllNotes(userRepository.findByUsername(principal.getName()).get().getId(), tags);
        return notesJSON;
    }

    @GetMapping("/notes/get-all-available-tags")
    public String getAllTagsApi(Principal principal) throws IOException {
        String tagsJSON = noteService.getAllTags(userRepository.findByUsername(principal.getName()).get().getId());
        return tagsJSON;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/notes/save-note", consumes="application/json")
    public void saveNoteAPI(@RequestBody NoteDto note,
                         Principal principal) {
        noteService.saveNote(userRepository.findByUsername(principal.getName()).get().getId(), note);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/notes/delete-note", consumes="application/json")
    public void deleteNoteAPI(@RequestBody Map<String, Integer> noteId){
        noteService.deleteNote(noteId.get("noteId"));
    }
}
