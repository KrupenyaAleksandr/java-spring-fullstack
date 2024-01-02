package spring.weblab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.weblab4.models.Note;
import spring.weblab4.models.User;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByuserId(User User);
}
