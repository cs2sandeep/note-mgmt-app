package travel.onarrival.notes.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import travel.onarrival.notes.services.NoteService;

@Component
public class AppInit implements CommandLineRunner {
    private final NoteService noteService;

    public AppInit(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    public void run(String... args) {
        noteService.initializeDatabase();
    }
}
