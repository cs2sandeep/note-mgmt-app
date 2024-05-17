package travel.onarrival.notes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.onarrival.notes.entities.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
