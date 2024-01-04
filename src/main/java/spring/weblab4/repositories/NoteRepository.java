package spring.weblab4.repositories;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.weblab4.models.Note;
import spring.weblab4.models.User;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByUserId(User User);
    List<Note> findAllByUserIdAndTag(User User, String tag);

    @Query("SELECT DISTINCT n.tag FROM Note n WHERE n.userId = :user AND n.tag IS NOT NULL")
    List<String> findUniqueTagsByUserId(User user);
}
