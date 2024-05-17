package travel.onarrival.notes.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travel.onarrival.notes.dao.NoteRepository;
import travel.onarrival.notes.entities.Note;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void initializeDatabase() {
        if(noteRepository.count() == 0) {
            noteRepository.saveAll(
                    List.of(
                            new Note("First title", "Content of first note"),
                            new Note("Second title", "Content of second note"),
                            new Note("Third title", "Content of third note")
                    )
            ).forEach(System.out::println);
        }
    }

    public List<Note> findAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> findNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}
