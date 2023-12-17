package spring.weblab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.weblab4.models.Note;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
